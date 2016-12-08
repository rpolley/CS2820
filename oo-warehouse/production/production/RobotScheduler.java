package production;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.ArrayList;

public class RobotScheduler implements FrameListener {// implements Time

	/**
	 * @author Jonathan Reinhart
	 *
	 */
	public RobotScheduler() {
		Master.master.subscribe(this);
		RobotLocs = new HashMap<Robot, int[]>();
		RequestQueue = new LinkedList<int[]>();
		ShelvesLocs = new HashMap<Integer, int[]>();
		TempShelfLocs = Master.master.getFloor().ShelfSpaceLocs;
	}

	/**
	 * @param doesn't
	 *            not take in anything, this is where we create the select
	 *            number of robots needed it is also going to add shelves to my
	 *            own hashmap of shelf locs;
	 * @return doesn't return anything, just creates the robots and sets them to
	 *         initial locations so there is a void return.
	 */
	public void addRobots() {
		Robot robo1 = new Robot(5, 5);
		int[] temp = new int[2];
		temp[0] = robo1.row;
		temp[1] = robo1.col;
		RobotLocs.put(robo1, temp);
		int tempcount = 0;
		for (Point i : TempShelfLocs) {
			int[] array1 = new int[2];
			array1[0] = i.row;
			array1[1] = i.col;
			ShelvesLocs.put(tempcount, array1);
			tempcount++;
		}
		charger=Master.master.getFloor().getChargerLocs();
		

	}

	public ArrayList<Point> TempShelfLocs;
	public int ordernumber;
	public ArrayList<Point> charger;
	// this is gonna be robots, and their locations
	// robots will each be a assigned an integer
	public Queue<int[]> RequestQueue;
	// this is an list of locations of shelves
	// Add all the shelve locations to this hashmap, because it will be easier
	// if we label each shelve
	// with [x,y] location as the int array.
	public HashMap<Integer, int[]> ShelvesLocs;
	public HashMap<Robot, int[]> RobotLocs;
	private int[] locids = new int[4];

	public void addRequest(Point shelf, Point destination) { // or an int as to
																// where the
		// picker/packer is or where
		// loading dock
		// [ x of shelf, y of shelf, x of packer or loading dock, y of packer or
		// loading dock]
		int locations[] = new int[4];
		locations[0] = shelf.row;
		locations[1] = shelf.col;
		locations[2] = destination.row;
		locations[3] = destination.col;
		RequestQueue.add(locations);
	}

	public void onFrame() {
		if (RequestQueue.isEmpty() && (locids[0] == 0 && locids[1] == 0 && locids[2] == 0 && locids[3] == 0)) {
			return;
		}
		for (Robot q : RobotLocs.keySet()) {
			if (q.inUse == false) {
				locids = RequestQueue.remove();
			}

		}
		Robot i = closestRobot(locids[0], locids[1]);
		if (i == null) {
			return;
		}
		if (i.state == -1 || i.state== 3) {
			i.state = 0;
		}
		makeMoveDecision(i, locids);
	}

	/**
	 *
	 * @param what
	 *            robot we are using, the x location to where we need to go, the
	 *            y location of where we need to
	 *
	 *            this id, is up to change but as of right now, i take an order
	 *            request like, item on shelf 2 move to the picker, or pick up
	 *            new inventory at the shipping docks and use shelf 4
	 * @return I don't return anything I update RobotLocs, which is robot
	 *         locations and go off whatever the queue is.
	 *
	 *         this method is the one moving the robots from their original x,y
	 *         coordinates to pick up a shelf then from that shelf take it
	 *         wherever it needs to go, then return that shelf to its orginal
	 *         location.
	 */
	public void moveRobot(Robot i, int torow, int tocol) {
		int toLocationRow = torow;
		int toLocationCol = tocol;
		// System.out.println(i.batterylife);
		int robotrow = i.row;
		int robotcol = i.col;
		// where we move the one robot to the shelve.
		// for each tick of the interface
		// trying to figure out a way to get around
		int check = 0;
		if (toLocationRow == robotrow && toLocationCol == robotcol) {
			if (i.state == 1) {
				i.arrivedatDestination = true;
			}

		}
		if (toLocationCol - robotcol < 0) {
			i.move(robotrow, robotcol - 1);
			// if it isnt a legal move, it wont move that means we can
			// do
			// something else
			// System.out.println(RobotLocs.get(i)[0]+"yolo");
			if (i.isLegalMove(robotrow, robotcol - 1) == false) {
				check = 0;
			} else {
				check = 1;
			}
		} else if (toLocationCol - robotcol > 0) {
			i.move(robotrow, robotcol + 1);
			if (i.isLegalMove(robotrow, robotcol + 1) == false) {
				check = 0;
			} else {
				check = 1;
			}
		}
		if (check == 0 && toLocationRow - robotrow < 0) {
			i.move(robotrow - 1, robotcol);
			check = 1;
		} else if (check == 0 && toLocationRow - robotrow > 0) {
			i.move(robotrow + 1, robotcol);
			check = 1;
		}
		if (check == 0) {
			if (toLocationRow - robotrow < 0) {
				i.move(robotrow + 1, robotcol);
			} else {
				i.move(robotrow - 1, robotcol);
			}
		}

		check = 0;
		// System.out.println("Robot x loc " + robotx + " Robot y loc " +
		// roboty);
		// System.out.println("Shelf x loc " + toLocationX + " Shelf y loc " +
		// toLocationY);
	}

	/**
	 * in the process of making this tickable to go along with Master class
	 */
	public void makeMoveDecision(Robot i, int[] locid) {

		// moving to shelf
		if (i.state == 0) {
			if (i.row == locid[0] && i.col == locid[1]) {
				i.hasShelves = true;
				List<Shelf> shelves = Master.master.getFloor().ShelfList;
				for (Shelf shelf : shelves) {
					if (shelf.HomeRow == i.row && shelf.HomeCol == i.col) {
						i.carrying = shelf;
					}

				}
				i.state = 1;
			} else {
				moveRobot(i, locid[0], locid[1]);
			}
			// or onFrame();

		} // moving to the belts
		else if (i.state == 1) {
			if (i.row == locid[2] && i.col == locid[3]) {
				i.state = 2;
			} else {
				moveRobot(i, locid[2], locid[3]);
			}
		} else if (i.state == 2) {
			if (i.row == locid[0] && i.col == locid[1]) {
				i.state = 3;
				i.carrying = null;
			} else {
				moveRobot(i, locid[0], locid[1]);
			}
		} else if(i.state==3){
			if(i.row == charger.get(0).row && i.col== charger.get(0).col){
				i.Charging();
				i.atCharger=true;
			}
			else{
			moveRobot(i, charger.get(0).row, charger.get(1).col);
			}
		}
		
		
		else {
			// resets robot
			i.arrivedatDestination = false;
			i.inUse = false;
			i.state = -1;

		}

		// }
	}

	/**
	 * move the robot to the charger, Im going to have to get rid of the
	 * parameter because tickable wont let me use the same robot as I want to.
	 * basically just move the robot to where the charger is, then while at that
	 * charger it will charge itself until full or close to full
	 *
	 * @param i
	 */

	/**
	 *
	 * @param x
	 *            closest robot to this combination of x,y coordinates, might be
	 *            changed to accept an int array to make life a bit easier
	 * @param y
	 * @return closest robot to the given x and y.
	 */
	public Robot closestRobot(int row, int col) {
		for (Robot i : RobotLocs.keySet()) {
			if (i.getBatteryLife() < .1) {
				i.inUse = true;
				continue;
			}
			i.inUse = true;
			return i;
		}
		return null;
	}
}

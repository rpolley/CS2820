
package production;
/**
 *
 * @author Jonathan Reinhart
 *
 */
public class Robot extends RobotScheduler {
	int row;
	int col;
	boolean hasShelves = false;
	boolean inUse = false;
	double batterylife = 1.0;
	boolean atCharger = false;
	//if it arrives at picker or something
	boolean arrivedatDestination=false;
	Shelf carrying;
	/*
	 * does what 'part1' used to, I think
	 * not even 100% sure what it does
	 */
	int state = -1;
/**
 * This just puts new robots down
 * @param startingx
 * @param startingy
 */
	public Robot(int startingx, int startingy) {
		this.row = startingx;
		this.col = startingy;
		hasShelves = false;
		inUse=false;
		int[] temp = new int[2];
		temp[0]=startingx;
		temp[1]=startingy;
		//RobotLocs.put(this, temp);
	}
	
	/**
	 * 
	 * @author James Vipond
	 * @param Robot to copy
	 * @result Copy Constructor
	 */
	public Robot(Robot original){
		row = original.row;
		col = original.col;
		hasShelves = original.hasShelves;
		inUse = original.inUse;
		atCharger = original.atCharger;
		batterylife = original.batterylife;
	}
	
	/**
	 * @param nothing, but we do need to know what robot we are talking about
	 * @return true if the robot is carrying shelves false otherwise
	 */
	public boolean isCarryingShelves(){
		if (this.hasShelves) {
			return true;
		}
		return false;
	}
	/**
	 * charges the robot once it runs below .1 batterylife
	 * then once it hits .9 or above we start doing stuff again with the
	 * robot
	 */
	public void Charging(){
		if(batterylife<.9){
			batterylife=batterylife+.1;
		}
		else{
			this.inUse=false; //was: inUse==false; 
			this.state=-1;
		}
	}
	// Makes sure to only move the robot 1 space at a time, and if it does only
	// move one space go ahead and do it.
	/**
	 *
	 * @param toX
	 * @param toY
	 * well its gonna move this robots location and update RobotLocs in robot scheduler to the new location
	 *
	 */
	public void move(int toX, int toY) {
		if (true||((row - toX) + (col - toY) == 1) || ((row - toX) + (col - toY) == -1)) {
			if (isLegalMove(toX, toY)) {
				// unfinished
				int[] newloc = new int[2];
				newloc[0] = toX;
				newloc[1] = toY;
				this.row=toX;
				this.col=toY;
				RobotLocs.remove(this);
				RobotLocs.put(this, newloc);
				//System.out.println(this.x);
				//System.out.println(RobotLocs.get(this)[0]);
				//System.out.println(newloc[0]+ "  "+newloc[1]);
				batterylife = batterylife - .001;

			}

		} else {
			System.out.println("Too big of a step, the robot has short feet");
		}
	}

	// Will check if there is something that is in the way, will see if it has
	// shelves, if so can't run into a shelve.
	/**
	 *
	 * @param toX
	 * @param toY
	 * @return boolean, if there is a shelf in the way don't go that way if our robot has a shelf already
	 * will return true if its a legal move and false otherwise
	 */
	public boolean isLegalMove(int torow, int tocol) {
		// for right now the only illegal move is when something with a shelf,
		// is runnign into a shelf.
		if (this.hasShelves == true) {
			for (Point q : TempShelfLocs) {
				if (q.row == torow && q.col == tocol) {
					if(this.state==2){
						return true;
					}
					return false;
				}
			}
		}
		return true;
	}

	// gets the batteryLife
	public double getBatteryLife() {
		return this.batterylife;
	}
	/**
	* @author James Vipond
	* @return row of the robot
	*/
	public int getRow(){
		return this.row;
	}
	
	/**
	* @param James Vipond
	* @return column of the robot
	*/
	public int getCol(){
		return this.col;
	}

	public Shelf getShelf() {
		return carrying;
	}
}
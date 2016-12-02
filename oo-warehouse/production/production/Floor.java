package production;

import java.util.ArrayList;

/*
 * @author Cole Petersen
 * 
 * Floor object:
 * Stores layout of the floor, including locations of belts, shelf spaces, and more. 
 */

public class Floor {
	
	int rows;
	int cols;
	int AmtRobots;
	
	Point point;
	
	public Object[][] layout;
	
	public ArrayList<Point> BeltLocs = new ArrayList<Point>();
	public ArrayList<Point> PackLocs = new ArrayList<Point>();
	public ArrayList<Point> PickLocs = new ArrayList<Point>();
	public ArrayList<Point> ShelfSpaceLocs = new ArrayList<Point>();
	public ArrayList<Point> ChargerLocs = new ArrayList<Point>();
	public Point RecDockLoc = new Point(0, 0);
	public ArrayList<Point> FloorSpaceLocs = new ArrayList<Point>();
	
	
	public Floor(int rows, int cols, int AmtRobots) {
		
		this.rows = rows;
		this.cols = cols;
		this.AmtRobots = AmtRobots;
		
		this.layout = new Object[rows][cols];
		
		int HighwayWidth;
		int AmtChargers;
		
		// If not enough room for required objects:
		
		if(rows < 4){
			throw new IllegalArgumentException(Integer.toString(rows));
		}
		
		if(cols < 4){
			throw new IllegalArgumentException(Integer.toString(cols));
		}
		
		// Maximum amount of chargers is ((x-3)/2)-1 (see charger placement below)
		// so maximum amount of robots is 2(((x-3)/2)-1).
		// That ratio of robots to floor size would be pretty unreasonable anyway.
		
		// If less than 1 robot or too many chargers required for available space:
		
		if(AmtRobots < 1 || AmtRobots > 2*(((rows-3)/2)-1)){
			throw new IllegalArgumentException(Integer.toString(AmtRobots));
		}
		
		// Set general highway width according to amount of robots.
		// This will not always be exact, but sets a guide for the floor layout.
		if(AmtRobots == 1){
			HighwayWidth = 1;
		}
		else if(AmtRobots > 1 && AmtRobots <= 10){
			HighwayWidth = 2;
		}
		else{
			HighwayWidth = 3;
		}
		
		// Set amount of chargers according to amount of robots.
		if(AmtRobots == 1){
			AmtChargers = 1;
		}
		else{
			AmtChargers = AmtRobots/2;
		}
		
		
		
		// put belt along left wall
		for(int i = 0; i < rows; i++){
			layout[i][0] = new MockBelt(i, 0);
			Point point = new Point(i, 0);
			this.BeltLocs.add(point);
		}
		
		// Put pick/pack stations at beginning and end of belt.
		// Size is 1 wide, HighwayWidth tall.
		for(int i = 0; i < HighwayWidth; i++){
			layout[i][1] = new Pack(i, 1);
			Point point = new Point(i, 1);
			this.PackLocs.add(point);
		}
		
		for(int i = rows-1; i > cols-HighwayWidth-1; i--){
			layout[i][1] = new Pick(i, 1);
			Point point = new Point(i, 1);
			this.PickLocs.add(point);
		}
		
		// Put shelf spaces of width 2 on floor.
		// Leave HighwayWidth spaces between them and other objects, including each other.
		// There are no horizontal breaks right now, but they can be added later.
		for(int k = HighwayWidth+1; k < rows-HighwayWidth-1; k = k+HighwayWidth+2){
			for(int i = k; i < k+2; i++){
				for(int j = HighwayWidth+2; j < cols-HighwayWidth; j++){
					layout[i][j] = new ShelfSpace(i, j);
					Point point = new Point(i, j);
					this.ShelfSpaceLocs.add(point);
				}
			}
		}
		
		// Put chargers along top wall, leaving a space between each and other objects for flexibility.
		// There should be enough space for all of the chargers with reasonable inputs.
		int ChargersPlaced = 0;
		for(int j = 3; j < cols - 2; j = j+2){
			if(ChargersPlaced < AmtChargers){
				layout[0][j] = new Charger(0, j);
				Point point = new Point(0, j);
				this.ChargerLocs.add(point);
				ChargersPlaced++;
			}
		}
		
		// put receiving dock in top right corner
		layout[0][cols-1] = new RecDock(0, cols-1);
		this.RecDockLoc = new Point(0, cols-1);
		
		// put generic Floor Spaces everywhere else
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < rows; j++){
				if(layout[i][j] == null){
					layout[i][j] = new FloorSpace(i, j);
					Point point = new Point(i, j);
					this.FloorSpaceLocs.add(point);
				}
			}
		}
		
	}
	
	int GetWidth(){
		return this.cols;
	}
	
	int GetHeight(){
		return this.rows;
	}
	
	ArrayList<Point> getBeltLocs(){
		return BeltLocs;
	}
	
	ArrayList<Point> getPackLocs(){
		return PackLocs;
	}
	
	ArrayList<Point> getPickLocs(){
		return PickLocs;
	}
	
	ArrayList<Point> getShelfSpaceLocs(){
		return ShelfSpaceLocs;
	}
	
	ArrayList<Point> getChargerLocs(){
		return ChargerLocs;
	}
	
	Point getRecDockLoc(){
		return RecDockLoc;
	}
	
	ArrayList<Point> getHighwayLocs(){
		return ChargerLocs;
	}
	
}


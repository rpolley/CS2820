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
	int AmtShelves;
	
	Point point;
	
	public Object[][] layout;
	
	public ArrayList<Point> BeltLocs = new ArrayList<Point>();
	public ArrayList<Point> PackLocs = new ArrayList<Point>();
	public ArrayList<Point> PickLocs = new ArrayList<Point>();
	public ArrayList<Point> ShelfSpaceLocs = new ArrayList<Point>();
	public ArrayList<Point> ChargerLocs = new ArrayList<Point>();
	public Point RecDockLoc = new Point(0, 0);
	public ArrayList<Point> FloorSpaceLocs = new ArrayList<Point>();
	public ArrayList<Shelf> ShelfList = new ArrayList<Shelf>();
	public int a;
	
	
	public Floor(int rows, int cols, int AmtRobots) {
		
		this.rows = rows;
		this.cols = cols;
		this.AmtRobots = AmtRobots;
		this.AmtShelves = 0;
		
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
		
		
		

		
		// Put pick/pack stations at beginning and end of belt. Store locations.
		// Size is 1 wide, HighwayWidth tall.
		Pack pack = null;
		Pick pick = null;
		for(int i = 0; i < HighwayWidth; i++){
			pack = new Pack(i, 1);
			layout[i][1] = pack;
			Point point = new Point(i, 1);
			this.PackLocs.add(point);
		}

		for(int i = rows-1; i > cols-HighwayWidth-1; i--){
			pick = new Pick(i, 1);
			layout[i][1] = pick;
			Point point = new Point(i, 1);
			this.PickLocs.add(point);
		}
		// put belt along left wall. Store belt locations.
		for(int i = 0; i < rows; i++){
			Point point = new Point(i, 0);
			layout[i][0] = new BeltSpace(i,0);
			this.BeltLocs.add(point);
		}
		Packer packer = new Packer(this, BeltLocs,1,1,pack);
		Picker picker = new Picker(this, BeltLocs,1,1,pick);
		new MockBelt(this,BeltLocs,packer,picker);
		
		// Put shelf spaces of width 2 on floor. Set up list of shelf spaces.
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
		// Set up list of chargers.
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
		
		// put receiving dock in top right corner, store location
		layout[0][cols-1] = new RecDock(0, cols-1);
		this.RecDockLoc = new Point(0, cols-1);
		
		// put generic Floor Spaces everywhere else, set up list of floor spaces
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < rows; j++){
				if(layout[i][j] == null){
					layout[i][j] = new FloorSpace(i, j);
					Point point = new Point(i, j);
					this.FloorSpaceLocs.add(point);
				}
			}
		}
		
		// put shelves on shelf spaces, set up list of shelves
		int j = 1;
		for(int i=0; i<ShelfSpaceLocs.size(); i++){
			
			Shelf shelf = new Shelf(j, ShelfSpaceLocs.get(i).row, ShelfSpaceLocs.get(i).col);
			this.ShelfList.add(shelf);
			this.AmtShelves++;
			j++;
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


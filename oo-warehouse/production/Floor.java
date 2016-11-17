package production;

/*
 * @author Cole Petersen
 * 
 * Floor object:
 * Stores layout of the floor, including locations of belts, shelf spaces, and more.
 */

public class Floor {
	
	int x;
	int y;
	int AmtRobots;
	public Object[][] layout;
	
	public Floor(int x, int y, int AmtRobots) {
		
		this.x = x;
		this.y = y;
		this.AmtRobots = AmtRobots;
		
		this.layout = new Object[x][y];
		
		int HighwayWidth;
		int AmtChargers;
		
		// If not enough room for required objects:
		
		if(x < 4){
			throw new IllegalArgumentException(Integer.toString(x));
		}
		
		if(y < 4){
			throw new IllegalArgumentException(Integer.toString(y));
		}
		
		// Maximum amount of chargers is ((x-3)/2)-1 (see charger placement below)
		// so maximum amount of robots is 2(((x-3)/2)-1).
		// That ratio of robots to floor size would be pretty unreasonable anyway.
		
		// If less than 1 robot or too many chargers required for available space:
		
		if(AmtRobots < 1 || AmtRobots > 2*(((x-3)/2)-1)){
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
		for(int i = 0; i < x; i++){
			layout[i][0] = new Belts(x, 0);
		}
		
		// Put pick/pack stations at beginning and end of belt.
		// Size is 1 wide, HighwayWidth tall.
		for(int i = 0; i < HighwayWidth; i++){
			layout[i][1] = new Pack(i, 1);
		}
		
		for(int i = x-1; i > x-HighwayWidth-1; i--){
			layout[i][1] = new Pick(i, 1);
		}
		
		// Put shelf spaces of width 2 on floor.
		// Leave HighwayWidth spaces between them and other objects, including each other.
		// There are no horizontal breaks right now, but they can be added later.
		for(int k = HighwayWidth+1; k < x-HighwayWidth-1; k = k+HighwayWidth+2){
			for(int i = k; i < k+2; i++){
				for(int j = HighwayWidth+2; j < y-HighwayWidth; j++){
					layout[i][j] = new ShelfSpace(i, j);
				}
			}
		}
		
		// Put chargers along top wall, leaving a space between each and other objects for flexibility.
		// There should be enough space for all of the chargers with reasonable inputs.
		int ChargersPlaced = 0;
		for(int j = 3; j < y - 2; j = j+2){
			if(ChargersPlaced < AmtChargers){
				layout[0][j] = new Charger(0, j);
				ChargersPlaced++;
			}
		}
		
		// put receiving dock in top right corner
		layout[0][y-1] = new RecDock(0, y-1);
		
		// put highways everywhere else
		for(int i = 0; i < x; i++){
			for(int j = 0; j < y; j++){
				if(layout[i][j] == null){
					layout[i][j] = new Highway(i, j);
				}
			}
		}
		
	}
	
}


package production;

/**
 * @author Cole Petersen
 * 
 * Charger object
 */

public class Charger extends DrivableSpaceType {
	
	public Charger(int row, int col){
		this.name = "Charger";
		this.row = row;
		this.col = col;
		this.CanDriveOn = true;
		this.HasRobot = true;	// assume the robot starts here
	}
	
}

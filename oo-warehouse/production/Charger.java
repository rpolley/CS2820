package production;

/*
 * @author Cole Petersen
 * 
 * Charger object
 */

public class Charger extends DrivableSpaceType {
	
	public Charger(int x, int y){
		this.name = "Charger";
		this.x = x;
		this.y = y;
		this.CanDriveOn = true;
		this.HasRobot = true;	// assume the robot starts here
	}
	
}

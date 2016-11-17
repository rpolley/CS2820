package production;

/*
 * @author Cole Petersen
 * 
 * Highway object
 */

public class Highway extends DrivableSpaceType {
	
	public Highway(int x, int y){
		this.name = "Highway";
		this.x = x;
		this.y = y;
		this.CanDriveOn = true;
		this.HasRobot = false;
	}
	
}

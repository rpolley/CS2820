package production;

/*
 * @author Cole Petersen
 * 
 * Picker object
 */

public class Pick extends DrivableSpaceType {
	
	public Pick(int x, int y){
		this.name = "Picking Station";
		this.x = x;
		this.y = y;
		this.CanDriveOn = true;
		this.HasRobot = false;
	}

}

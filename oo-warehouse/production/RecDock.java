package production;

/*
 * @author Cole Petersen
 * 
 * Receiving Dock object
 */

public class RecDock extends DrivableSpaceType {
	
	public RecDock(int x, int y){
		this.name = "Receiving Dock";
		this.x = x;
		this.y = y;
		this.CanDriveOn = true;
		this.HasRobot = false;
	}

}

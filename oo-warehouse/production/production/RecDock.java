package production;

/**
 * @author Cole Petersen
 * 
 * Receiving Dock object
 */

public class RecDock extends DrivableSpaceType {
	
	public RecDock(int row, int col){
		this.name = "Receiving Dock";
		this.row = row;
		this.col = col;
		this.CanDriveOn = true;
		this.HasRobot = false;
	}

}

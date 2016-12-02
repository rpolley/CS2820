package production;

/*
 * @author Cole Petersen
 * 
 * Floor Space (highway) object
 */

public class FloorSpace extends DrivableSpaceType {
	
	public FloorSpace(int row, int col){
		this.name = "Floor Space";
		this.row = row;
		this.col = col;
		this.CanDriveOn = true;
		this.HasRobot = false;
	}
	
}

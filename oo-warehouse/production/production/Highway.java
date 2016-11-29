package production;

/*
 * @author Cole Petersen
 * 
 * Highway object
 */

public class Highway extends DrivableSpaceType {
	
	public Highway(int row, int col){
		this.name = "Highway";
		this.row = row;
		this.col = col;
		this.CanDriveOn = true;
		this.HasRobot = false;
	}
	
}

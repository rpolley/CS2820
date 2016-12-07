package production;

/**
 * @author Cole Petersen
 * 
 * PickerSpace object
 */

public class PickerSpace extends DrivableSpaceType {
	
	public PickerSpace(int row, int col){
		this.name = "Picking Station";
		this.row = row;
		this.col = col;
		this.CanDriveOn = true;
		this.HasRobot = false;
	}

}

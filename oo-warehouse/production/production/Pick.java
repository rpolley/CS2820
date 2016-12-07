package production;

/**
 * @author Cole Petersen
 * 
 * Picker space object
 */

public class Pick extends DrivableSpaceType {
	
	public Pick(int row, int col){
		this.name = "Picking Station";
		this.row = row;
		this.col = col;
		this.CanDriveOn = true;
		this.HasRobot = false;
	}

}

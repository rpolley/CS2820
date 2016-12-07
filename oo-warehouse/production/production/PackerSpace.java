package production;

/**
 * @author Cole Petersen
 * 
 * PackerSpace object
 */

public class PackerSpace extends DrivableSpaceType {
	
	public PackerSpace(int row, int col){
		this.name = "Packing Station";
		this.row = row;
		this.col = col;
		this.CanDriveOn = true;
		this.HasRobot = false;
	}

}

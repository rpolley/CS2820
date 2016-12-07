package production;

/**
 * @author Cole Petersen
 * 
 * Packer space object
 */

public class Pack extends DrivableSpaceType {
	
	public Pack(int row, int col){
		this.name = "Packing Station";
		this.row = row;
		this.col = col;
		this.CanDriveOn = true;
		this.HasRobot = false;
	}

}

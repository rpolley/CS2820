package production;

/**
 * @author Cole Petersen
 * 
 * BeltSpace object
 */

public class BeltSpace extends SpaceType{
	public BeltSpace(int row, int col){
		this.row = row;
		this.col = col;
		this.CanDriveOn = false;
		this.name = "Belt";
	}
}

package production;

/*
 * @author Cole Petersen
 * 
 * Shelf Space object
 */

public class ShelfSpace extends DrivableSpaceType {

	public boolean HasShelf;
	
	public ShelfSpace(int row, int col){
		this.name = "Shelf Space";
		this.row = row;
		this.col = col;
		this.HasShelf = true;
		this.CanDriveOn = false;
		this.HasRobot = false;
	}
	
	public boolean CanDriveOn(){
		return !HasShelf;
	}
	
}

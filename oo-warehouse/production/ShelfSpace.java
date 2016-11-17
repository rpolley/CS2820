package production;

/*
 * @author Cole Petersen
 * 
 * Shelf Space object
 */

public class ShelfSpace extends DrivableSpaceType {

	public boolean HasShelf;
	
	public ShelfSpace(int x, int y){
		this.name = "Shelf Space";
		this.x = x;
		this.y = y;
		this.HasShelf = true;
		this.CanDriveOn = false;
		this.HasRobot = false;
	}
	
	public boolean CanDriveOn(){
		return !HasShelf;
	}
	
	
}

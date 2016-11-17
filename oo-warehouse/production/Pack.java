package production;

/*
 * @author Cole Petersen
 * 
 * Packer object
 */

public class Pack extends DrivableSpaceType {
	
	public Pack(int x, int y){
		this.name = "Packing Station";
		this.x = x;
		this.y = y;
		this.CanDriveOn = true;
		this.HasRobot = false;
	}

}

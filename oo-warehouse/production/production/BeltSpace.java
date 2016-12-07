package production;

public class BeltSpace extends SpaceType{
	public BeltSpace(int row, int col){
		this.row = row;
		this.col = col;
		this.CanDriveOn = false;
		this.name = "Belt";
	}
}

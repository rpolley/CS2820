package production;

import java.util.ArrayList;

public class Packer extends PackerSpace {
	public ArrayList<Point> beltarea;

	public Packer(ArrayList<Point> beltarea, int row, int col) {
		super(row, col);
		this.beltarea = beltarea;
	}

	public void processItems() {
		// TODO Auto-generated method stub
	}

}
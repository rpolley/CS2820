package production;

import java.util.ArrayList;

/**
 * 
 * @author Anani
\ * bad code removed by rpolley
 */
public class Packer extends PackerSpace{
 	public  ArrayList<Point> beltarea;
 	
 	public Packer(ArrayList<Point> beltLocs, int row, int col){
 		super(row, col);
		this.beltarea = beltLocs;
 	}
	public void processItems() {
		// TODO Auto-generated method stub
		
	}

}

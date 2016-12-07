package production;

import java.util.ArrayList;

/**
 * 
 * @author Anani
 *
 */
public class Picker extends PickerSpace{
	private ArrayList<Point> beltarea;
	public Picker(ArrayList<Point> beltarea, int row, int col){
		super(row,col);
		this.beltarea = beltarea; 		
	}
	


	public void processItems() {
		// TODO Auto-generated method stub
		
	}

}

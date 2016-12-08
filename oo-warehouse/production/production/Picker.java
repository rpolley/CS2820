package production;

import java.util.ArrayList;

/**
 * 
 * @author Anani
 *
 */
public class Picker extends PickerSpace implements FrameListener{
	private ArrayList<Point> beltarea;
	public Picker(ArrayList<Point> beltarea, int row, int col){
		super(row,col);
		this.beltarea = beltarea; 		
	}
	
	public void onFrame(){
		if(Master.master.getRobotScheduler().closestRobot(row, col).arrivedatDestination){
			processItems();
		}
	}
	
	public void processItems() {
		// TODO Auto-generated method stub
		
	}

}

package production;

import java.util.ArrayList;

/**
 * 
 * @author Anani
 *
 */
public class Picker {
	public String[][] Picker;
	private Pick location; 	
	public Picker(Floor n,ArrayList<Point> beltarea, Pick p){
		this.location = p;
	}
	
	public Pick getLocation(){
		return location;
	}

	public void processItems() {
		// TODO Auto-generated method stub
		
	}

}

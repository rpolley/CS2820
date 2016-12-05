package production;

import java.awt.Point;
import java.util.ArrayList;

/**
 * 
 * @author Anani
 *
 */
public class Picker {
	Floor n;
	public String[][] Picker;
	private int statepicker; // state picker station
	private int Pickerp; //presence of picker on the Floor
	private int L; // Belt length 	
 	public  ArrayList<Point> beltarea;
 	
	public Picker(Floor n,ArrayList<Point> beltarea, int Pickerp,int statepicker){
		this.n = n;
		beltarea = n.getBeltLocs();	
		L=beltarea.size();
		this.Pickerp=Pickerp;
		this.statepicker=statepicker;
		Picker=new String[L][0]; //Picker position
		init();
	}
	
	private void init(){ 
 		
 		Pickerp=1; // Picker is present 		
 		// If state is equal to 1 Picker is free
 		//  State = 2  picker is busy making Bin 
 		// State = 3 Packer or Picker put the product on the Belt 		
 		statepicker=1; 
 		 
 	} 
	public void updateState(){		 		 
		 	//Update Picker States		
		 				if ("B".equals(Picker[L][0])){ 
		 					statepicker=3; 
		 				}else if(Picker[L][0]==null){ 
		 					statepicker=1; 
		 				}else statepicker=2; 
		 		
	}
	
	public int getStatePicker(){
		//init();
 		return statepicker; 
 	}  	
	public int getPickerp(){ 
 		return Pickerp; 
	} 
	public String[][] getPicker(){ 
 		return Picker; 
 	} 

}

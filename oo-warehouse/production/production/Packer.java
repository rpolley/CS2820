package production;

import java.awt.Point;
import java.util.ArrayList;

/**
 * 
 * @author Anani
 *
 */
public class Packer {
	Floor n;
	public String[][] Packer; 	
	private int Packerp; //presence of packer on the floor 	
 	private int statepacker;//state packer position
 	private int L; // Belt length 	
 	public  ArrayList<Point> beltarea;
 	
 	public Packer(Floor n,ArrayList<Point> beltarea, int Packerp, int statepacker){
 		this.n = n;
		beltarea = n.getBeltLocs();
 		this.Packerp=Packerp; 		
 		this.statepacker=statepacker;
 		Packer=new String[L-140][0];//Packer postion 
 		
 	}
 	
 	private void init(){ 
 		
 		Packerp=1; // Packer is present
 		// If state is equal to 1 Packer or Picker is free
 		//  State = 2 packer or picker is busy making Bin or Parcel
 		// State = 3 Packer or Picker put the product on the Belt 		
 		statepacker=1; 
 	} 
 	
 	public void updateState(){		 		 
 		 
 			// Update Packer states		 
 		 				if ("p".equals(Packer[L-140][0])){ 
 		 					statepacker=3; 
 		 				}else if(Packer[L-140][0]==null){ 
 		 					statepacker=1; 
 		 				}else statepacker=2;		 					 		
 		 	}
 	public int getStatePacker(){ 
 		return statepacker; 
 	}  	
	public int getPackerp(){ 
 		return Packerp; 
	} 
	public String[][] getPacker(){ 
 		return Packer; 
 	} 

}

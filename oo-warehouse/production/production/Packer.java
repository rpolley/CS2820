package production;

import java.util.ArrayList;

/**
 * 
 * @author Anani
 * bad code removed by rpolley
 */
public class Packer{
	Floor n; 	
	private Pack location;
 	public  ArrayList<Point> beltarea;
 	
 	public Packer(Floor n,ArrayList<Point> beltarea, Pack p){
 		this.n = n;
		beltarea = n.getBeltLocs();
 		this.location = p;
 		
 	}
 	private Pack getLocation(){
 		return location;
 	}
	public void processItems() {
		// TODO Auto-generated method stub
		
	}

}

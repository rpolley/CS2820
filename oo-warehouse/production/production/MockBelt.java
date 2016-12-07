package production;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
	/**
	 * 
	 * @author Anani
	 * fixed by rpolley
	 */
public class MockBelt implements Belt{		
		Floor n;
		Packer pa;
		Picker pi;
	 	private int L; // Belt length
	 	//private int nC; // number of Items
	 	public  BeltSpace[] beltarea;//if you change this back to String[] your commit dies
	 	private Queue<Bin> bins;
		//Call Floor object
		//to locate the belt area (place),cells and other
		//Initialisation Bin to null
	public MockBelt(Floor n,ArrayList<Point> beltarea, Packer pa, Picker pi){
		this.n = n;
		this.pa = pa;
		this.pi = pi;
		beltarea = n.getBeltLocs();
		bins = new LinkedList<Bin>();
		L=beltarea.size();
		this.beltarea = new BeltSpace[L];
		for(int i = 0; i<L; i++){
			Point loc = beltarea.get(i);
			this.beltarea[i] = new BeltSpace(loc.row,loc.col);
		}
 		//this.nC=nC	
	}
	
	public void tick(int count){
		for(Bin bin:bins){
			bin.move();
		}
		pi.processItems();
		pa.processItems();
	}
	public boolean binAvailable(){
		if(bins.size()>0&&bins.peek().atEnd()){
			return true;
		}
		else{
			return false;
		}
	}

	@Override
	public Bin getBin() {
		return bins.poll();
	}
}

package production;

import java.awt.List;
import java.awt.Point;
import java.util.*;
	/**
	 * 
	 * @author Anani
	 *
	 */
public class MockBelt implements Belt{		
		Floor n;
		Packer pa;
		Picker pi;
		Bin binMade;		
	 	private int L; // Belt length
	 	//private int nC; // number of Items
	 	public  String[] beltarea;
		//Call Floor object
		//to locate the belt area (place),cells and other
		//Initialisation Bin to null
	public MockBelt(Floor n,String[] beltarea, Packer pa, Picker pi){
		this.n = n;
		this.pa = pa;
		this.pi = pi;
		beltarea = n.getBeltLocs();
		binMade = null;
		L=beltarea.length;  		
 		//this.nC=nC
 		this.beltarea=new String[L]; 
		System.arraycopy(beltarea, 0, this.beltarea, 0, L); 		
 		init(); 
	}
	private void init(){ 
 		for (int i=0; i<L; i++){			
 			
 			beltarea[i]=null; // An empty Belt		 							 
 		} 
 		
 	} 
	
	public void tick(int count){
		
		if (binMade!= null){
			if (!binMade.isFinished())return;
			int m = pa.getPackerp();
			if (m!=1)return; //Packer present
			
			Cell c = n.getCell(pa.getPacker());
			if (c.getContents()!=null)return;
			c.setContents(binMade);
			binMade = null;		
		}
		if (!isMovable())return;
		
	}
	private boolean isMovable(){
		
		if (binMade != null)return false;	
		for (String p: beltarea){
			Cell c= n.getCell(p);
			Object o  = c.getContents();
			if (o==null);
			if ((o instanceof Bin) && !((Bin)o).isFinished())return false;
			if ((o instanceof Parcel) && !((Parcel)o).isFinished())return false;
		}
		return true;
	}

	private void doPacker(){
		
		String[][] W= new String[L][0];
		int[] action=new int[]{1,2,3};
 		int k;
 		
 		for(int i=0; i<L; i++){ 
 				System.arraycopy(pa.Packer[i], 0, W[i], 0,L); 
 			}
 		for (int i=0; i<action.length; i++){
 		// Take Bin from the belt
			if (action[i]==1){ 
 				k=0; 
 				while(k>=0){ 
					if(W[L-140][k]==null){ 
 						W[L-140][k]=beltarea[L-140]; 
 						k=-100;						 
					} 
				k+=1; 
 				} 
				beltarea[L-140]=null; 
 			}
			
			// Make the Parcel
 			if(action[i]==3){ 		 				 
 					W[L-140][0]=null; 
				} 
				W[L-140][0]="P"; 
 			//Put the parcel on the belt
 			if (action[i]==4){ 
			beltarea[L-140]="P"; 
			W[L-140][0]=null; 
 				 
 			}
 		}
		Cell c = n.getCell();
		Object o = c.getContents();
		assert o instanceof Bin;
		Bin b = (Bin)o;
		order v = b.getOrder();
		Parcel m = new Parcel(v.getAddress(), v.getOrderItems());
		c.setContents(m);
	}

	public boolean binAvailable() {
		if(binMade != null){
		return false;
		}
	Cell c = n.getCell((pi.Picker));
	if (c.getCell()!=null)	return false;
	return true;
	}

	public Bin getBin(){
		assert binMade==null;
		binMade = new Bin();
		return binMade;
		}	

	}
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
		Bin binMade;
		public String[][] Picker;
		public String[][] Packer; 
	 	private int Pickerp; //presence of picker on the Floor
		private int Packerp; //presence of packer on the floor
	 	private int statepicker; // state picker station
	 	private int statepacker;//state packer position 
	 	private int L; // Belt length
	 	//private int nC; // number of Items
	 	public  String[] beltarea;
		//Call Floor object
		//to locate the belt area (place),cells and other
		//Initialisation Bin to null
	public MockBelt(Floor n,String[] beltarea,int Pickerp, int Packerp,int statepicker, int statepacker){
		this.n = n;
		beltarea = n.getBeltLocs();
		binMade = null;
		L=beltarea.length;
 		this.Pickerp=Pickerp;
 		this.Packerp=Packerp;
 		this.statepicker=statepicker;
 		this.statepacker=statepacker;
 		//this.nC=nC
 		this.beltarea=new String[L]; 
		System.arraycopy(beltarea, 0, this.beltarea, 0, L); 
 		Picker=new String[L][0]; //Picker position
 		Packer=new String[L-140][0];//Packer postion 
 		
 		init(); 
	}
	private void init(){ 
 		for (int i=0; i<L; i++){			
 			
 			beltarea[i]=null; // An empty Belt		 							 
 		} 
 		
 		Pickerp=1; // Picker is present
 		Packerp=1; // Packer is present
 		// If state is equal to 1 Packer or Picker is free
 		//  State = 2 packer or picker is busy making Bin or Parcel
 		// State = 3 Packer or Picker put the product on the Belt
 		
 		statepicker=1; 
 		statepacker=1; 
 	} 
	public void updateState(){		 		 
	 /*	//Update Picker States		
	 				if ("B".equals(Picker[L][0])){ 
	 					statepicker=3; 
	 				}else if(Picker[L][0]==null){ 
	 					statepicker=1; 
	 				}else statepicker=2;	 		 
	 	*/	
		// Update Packer states		 
	 				if ("p".equals(Packer[L-140][0])){ 
	 					statepacker=3; 
	 				}else if(Packer[L-140][0]==null){ 
	 					statepacker=1; 
	 				}else statepacker=2;		 					 		
	 	} 

	public void tick(int count){
		
		if (binMade!= null){
			if (!binMade.isFinished())return;
			Cell c = n.getCell(n.getPacker());
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
		int[] action=new int[]{1,2,3,4};
 		int k;
 		
 		for(int i=0; i<L; i++){ 
 				System.arraycopy(Packer[i], 0, W[i], 0,L); 
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
		Cell c = n.getCell(n.getPacker());
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
	Cell c = n.getCell((n.picker));
	if (c.getCell()!=null)	return false;
	return true;
	}

	public Bin getBin(){
		assert binMade==null;
		binMade = new Bin();
		return binMade;
		}	

	}
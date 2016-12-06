package production;

import java.util.ArrayList;
	/**
	 * 
	 * @author Anani
	 *
	 */
public class BeltArea implements Belt{		
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
	public BeltArea(Floor n,ArrayList<Point> beltarea, Packer pa, Picker pi){
		this.n = n;
		this.pa = pa;
		this.pi = pi;
		beltarea = n.getBeltLocs();
		binMade = null;
		L=beltarea.size();  		
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
			pi.Picker[L][0]="B";
			/*Cell c = n.getCell(pa.getPacker());
			if (c.getContents()!=null)return;
			c.setContents(binMade);*/
			binMade = null;		
		}
		if (!isMovable())return;
		beltarea[L-140]="B";
		pi.Picker[L][0]=null;
		doPacker();
		
	}
	private boolean isMovable(){
		
		if (binMade != null)return false;
		int m = pa.getPackerp();//check if
		if (m!=1)return false; //Packer is present		
		for (int i=0; i<L; i++){
			if (beltarea[i]!= null)return false;			
		}
		int r = pa.getStatePacker();
		if (r!=1)return false;
		int u = pi.getStatePicker();
		if (u!=1)return false;		
		return true;

		/*for (String p: beltarea){
			//Cell c= n.getCell(p);
			String[] c=  beltarea;
			Object o  = c.getContents();
			if (o==null);
			if ((o instanceof Bin) && !((Bin)o).isFinished())return false;
			if ((o instanceof Parcel) && !((Parcel)o).isFinished())return false;
		}*/
	}

	private void doPacker(){
		
		String[][] W= new String[L][0];
		int[] action=new int[]{1,2,3,4}; 		
 		
 		for(int i=0; i<L; i++){ 
 				System.arraycopy(pa.Packer[i], 0, W[i], 0,L); 
 			}
 		for (int i=0; i<action.length; i++){
 		// Take Bin from the belt
			if (action[i]==1){
				
					if(W[L-140][0]!=null)return; 
 						W[L-140][0]=beltarea[L-140];
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
		/*Cell c = n.getCell();
		Object o = c.getContents();
		assert o instanceof Bin;
		Bin b = (Bin)o;
		order v = b.getOrder();
		Parcel m = new Parcel(v.getAddress(), v.getOrderItems());
		c.setContents(m);*/
	}

	public boolean binAvailable() {
		if(binMade != null){
		return false;
		}
		if(pi.Picker!=null)return false;	 
		return true;
	}

	public Bin getBin(){
		assert binMade==null;
		binMade = new Bin();
		return binMade;
		}	

	}
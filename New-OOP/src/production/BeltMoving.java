package production;

import java.util.ArrayList; 
import java.util.Random; 

public class BeltMoving {	
	
		public String[] belt; 
	 	private int[] beltState; 
	 	private int L; 
		private int nC; 
		ArrayList<String> beltOutput = new ArrayList<String>(); 
		
		public void Belt(int L, int nC){ 
			this.L = L; 
			this.nC = nC; 
			belt = new String[L]; 
	 		for (int i=0; i<L; i++){ 
	 			belt[i]=null; 
	 		} 
			beltState = new int[L]; 
	 		 
			init(); 	 		 
	 	} 
		
	 	private void init(){ 
			//add new component to the belt 
			Random randomGenerator = new Random(); 
	 		int value = randomGenerator.nextInt(nC+1); 
			if (value>0){ 
	 			value = value+64; 
	 			belt[0] = Character.toString((char)value); 
	 			 
	 		} else { 
				belt[0]=null; 
			} 
	 		updateBeltState(); 
	 		 
	 	} 
	 	private void updateBeltState(){ 
			for(int i=0; i<L; i++){ 
	 			if (belt[i]==null){ 
					beltState[i]=3; 
				}else if ("P".equals(belt[i])){  
	 				beltState[i] = 2; 
	 			}else beltState[i] = 1; 
			} 
	 		 
	 	} 
	 	public void updateBelt(String[] B){ 
	 		System.arraycopy(B, 0, belt, 0, L); 
	 		updateBeltState(); 
	 	} 
	 	 
	 	public void slideBelt(){ 
	 		String[] beltTemp = new String[L]; 
	 		if (belt[L-1]!=null){ 
	 			beltOutput.add(belt[L-1]); 
	 			 
	 		} 
			for (int i=1; i<L; i++){ 
	 			beltTemp[i]=belt[L-1]; 
	 			 
			} 
	 		System.arraycopy(beltTemp, 0, belt, 0, L); 
			init(); 
			 
	 	} 
	 	public int[] getBeltState(){ 
	 		return beltState; 
		} 
	} 


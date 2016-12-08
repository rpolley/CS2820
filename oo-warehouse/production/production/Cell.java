package production;

public class Cell extends Point {
	
	private Object contents;  // Robot or Shelf 
	  
	   Cell(int row, int col) { 
	 	super(row,col); contents = null;  
	 	} 
	   Object getContents() { 
	 	return contents; 
	     }    
	  
	   void setContents(Object O) { 
	 	contents = O;   
	     } 
	   /** 
	    * Display Cell as a string 
	    */ 
	   public String toString() { 
	     String result = super.toString(); 
	     if (contents instanceof Bin) result += " contains Bin"; 
	     if (contents instanceof Parcel) result += " contains Parcel"; 
	     return result; 
	     } 
	   /** 
	    * Provide a clone() method for Visualizer; this code has a  
	    * BUG - the clone is not a deep copy (to make it a deep copy, 
	    * things like Robot, Shelf, etc, would all need to implement 
	    * clone as well). 
	    */ 
	  public Object clone() { 
		Cell n = new Cell(this.row,this.col); 
		n.contents = this.contents; 		
		return n; 
	     } 
	  } 




package production;

public class Parcel {
	String address; 
	   Orders[] items; 
	   boolean finished; 
	   public Parcel(String address, Orders[] items) {  
	 	this.address = address; 
	 	this.items = items; 
	 	finished = false; 
	     } 
	   public boolean isFinished() { return finished; } 
	   public void setFinished() { finished = true; } 
	  public String toString() { return "Parcel"; } 


}

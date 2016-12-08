package production;
/**
 * 
 * @author Anani
 * Bin full with Items Ordered
 * and available to push on the belt
 */

public class Bin {	
	order Order;
	boolean finished;		
	public Bin(){	
		Order = null; finished = false;}
	public boolean isFinished(){
		return finished;}
	public void setFinished(){
		finished = true;}
	public order getOrder(){
		return Order;}
	public void setOrder(order o){
		Order = o;}	
	public String toString(){
		return "Bin";
	}

}

package production;
/**
 * 
 * @author Anani
 * Bin full with Items Ordered
 * and available to push on the belt
 */

public class Bin {
	Order Order;
	boolean finished;
	public Bin(){
		Order = null; finished = false;}
	public boolean isFinished(){
		return finished;}
	public void setFinished(){
		finished = true;}
	public Order getOrder(){
		return Order;}
	public void setOrder(order o){
		Order = o;}	
	public String toString(){
		return "Bin";
	}

}

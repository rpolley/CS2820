package production;
/**
 * 
 * @author Anani
 * Bin full with Items Ordered
 * and available to push on the belt
 */

public class Bin {
	Orders Order;
	boolean finished;
	public Bin(){
		Order = null; finished = false;}
	public boolean isFinished(){
		return finished;}
	public void setFinished(){
		finished = true;}
	public Orders getOrder(){
		return Order;}
	public void setOrder(Orders o){
		Order = o;}	
	public String toString(){
		return "Bin";
	}

}

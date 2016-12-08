package production;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Anani
 *
 */
public class Picker extends PickerSpace implements FrameListener{
	private ArrayList<Point> beltarea;
	private Inventory i;
	Map<Integer, String> orders;
	public Picker(ArrayList<Point> beltarea, int row, int col){
		super(row,col);
		this.beltarea = beltarea;
		i = Master.master.getInventory();
		orders = new HashMap<Integer,String>()
	}
	
	public void onFrame(){
		Robot r = Master.master.getRobotScheduler().closestRobot(row, col);
		if(r.arrivedatDestination){
			Shelf s = r.getShelf();
			String item = orders.get(s.id);
			processItem(item);
		}
	}
	
	public void processItem(String itemName) {
		i.removeItem(itemName, 1);
		MockBelt b = Master.master.getFloor().getBelt();
		b.getBins().add(new Bin(b));
	}
	public void addOrder(String item, int shelfid){
		
	}
}

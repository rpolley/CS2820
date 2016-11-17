package production;
import java.util.HashMap;
import java.util.Set;
import java.util.Collection;

/**
 * @author: Chaitanya Kovuri
 * git username: itsck
 * Warehouse Final Project: Orders
 */

public class order {

	//All variables are declared here.
	int OrderID;
	HashMap<String,Integer> itemNamesAndQty;
	String address;
	String status;

    //constructor
        /**
         *
         * @param OrderID
         * @param itemsQty Map of Items and desired Quantities
         * @param address
         */
	public order (int OrderID, HashMap<String,Integer> itemsQty, String address){
		this.OrderID = OrderID;
		this.itemNamesAndQty = itemsQty;
		this.address = address;
		this.status = "arrived"; //order items are present in warehouse and hence order has "arrived" to the warehouse.
	}

	//update the order status
	public void updateStatus(String newStatus){
		this.status = newStatus;
		}

	/**All the Getters & Setters
	 * are below.
	*/

	//Get OrderID
	public int getOrderID(){
		return this.OrderID;
	}

	//Get number of items in the order
	public int getNumberOfItems(){
		int itemLength = itemNamesAndQty.size();
		return itemLength;
	}

	//Get the Set of items in the order
	public Set getSetOfItems(){
		return itemNamesAndQty.keySet();
	}

	//Get the Set of desired Item Quantities
	public Collection<Integer> getItemQty(){
		return itemNamesAndQty.values();
	}

	//Get the order address
	public String getAddress(){
		return this.address;
	}

	//Get status
	public String getLocation(){
		return this.status;
	}


}

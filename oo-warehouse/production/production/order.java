package production;
import java.util.HashMap;
import java.util.Map;
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
	String[] itemNames;
	Integer[] itemRelatedQty;
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
		this.itemNames = new String[itemsQty.size()]; // Stores item names in an array
		this.itemRelatedQty = new Integer[itemsQty.size()]; //Stores item quantities in same order as the above
		this.OrderID = OrderID;
		this.itemNamesAndQty = itemsQty;
		this.address = address;
		this.status = "Arrived"; //order items are present in warehouse and hence order has "Arrived" to the warehouse.
		translateHashMap(itemsQty); 
	}

	//update the order status
	public void updateStatus(String newStatus){
		this.status = newStatus;
		}
	
	//Convert the HashMap keys and values into arrays for later use.
	public void translateHashMap(HashMap<String,Integer> itemsQty){
		int index = 0;
		for (HashMap.Entry<String,Integer> mapEntry: itemsQty.entrySet()){
			itemNames[index] = mapEntry.getKey();
			itemRelatedQty[index] = mapEntry.getValue();
			index++;
		}
		
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

	//Get the Array of items in the order
	public String [] getArrayOfItemNames(){
		return itemNames;
	}

	//Get the desired Item Quantities relating to items in the same order.
	public Integer[] getItemQty(){
		return itemRelatedQty;
	}

	//Get the order address
	public String getAddress(){
		return this.address;
	}

	//Get status
	public String getLocation(){
		return this.status;
	}

	public String getStatus(){
		return this.status;
	}

}

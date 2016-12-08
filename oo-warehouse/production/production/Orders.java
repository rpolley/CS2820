package production;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Collection;

/**
 * @author: Chaitanya Kovuri
 * git username: itsck
 * Warehouse Final Project: Orders
 */

public class Orders implements FrameListener{

	
	HashMap<Integer, order> initialOrders;
	HashMap<Integer, Integer> remainingOrderItems;
	Queue<Integer> ordersQueue; //LinkedList and the Integer is the OrderID
	private Inventory I;
	private RobotScheduler RS;
	private Floor F;
	private Point P;
	boolean pickerNotification=false;
	//for testing
	HashMap<String,Integer> order1 = new HashMap<String,Integer>();
	HashMap<String,Integer> order2 = new HashMap<String,Integer>();
	HashMap<String,Integer> order3 = new HashMap<String,Integer>();


	/**
	 * @author Chaitanya Kovuri
     * @param None
     * 
     */
	public Orders () {
		//using "order" instead of Object because
		//wont have to cast back while calling getters.
        Master.master.subscribe(this);
		initialOrders  = new HashMap<Integer,order>(); //Contains OrderID and other order class Details
		ordersQueue = new LinkedList<Integer>(); // Contains only the OrderID
		remainingOrderItems = new HashMap<Integer, Integer>();
		I = Master.master.getInventory();
		//F = new Floor(10,10,1);
		//for testing
		order1.put("A",25);
    	order1.put("Y", 11);
    	order1.put("B",15);
    	order1.put("C",14 );
    	order1.put("D",3);
    	order1.put("G",2 );
    	
    	order2.put("H",41); //41 Quantities out of 50
    	order2.put("I",34);
    	order2.put("J",12);
    	order2.put("K",1);
    	order2.put("L",19);
    	
    	order3.put("H",10); //H should have already been reduced to 9 Qty from order2
    	order3.put("I",34);
    	order3.put("J",12);
    	order3.put("K",1);
		initialOrders.put(101, new order(101, order1, "Iowa"));
		initialOrders.put(110, new order(110, order2, "California"));
		initialOrders.put(111, new order(111, order3, "Toronto"));
		ordersQueue.add(101);
		ordersQueue.add(110);
		ordersQueue.add(111);
	}

        public void onFrame(){
        	Integer OrderID = ordersQueue.peek(); //Returns value at the head, otherwise returns null.
        	if (OrderID==null){ //Null Pointer Exception because cant compare int with null. Must be Integer
        		System.out.println("No orders to process");
        		return;
        	}
        	order currentOrder = initialOrders.get(OrderID);
        	String orderStatus = currentOrder.getStatus();
        	if (orderStatus.matches("In Progress")==true){
        		int numOfRemainingItems = remainingOrderItems.get(OrderID);
        		if(numOfRemainingItems==0){
        			orderFulfilled(OrderID);
        			remainingOrderItems.remove(OrderID);
        			Integer newOrderID = ordersQueue.peek();
        			prepareForNextOrder(newOrderID, remainingOrderItems);
        			return;
        		}
        		else{
        			Point shelfPosition = itemShelfLoc(currentOrder, numOfRemainingItems-1);
        			Point pickerStation = new Point(9,2); //Hardcoding it for Demo
        			RS.addRequest(shelfPosition, pickerStation);
        			Integer updateRemItems = numOfRemainingItems-1;
        			remainingOrderItems.put(OrderID, updateRemItems);
        			return;
        		}
        	}
           if (orderStatus.matches("Arrived")==true){
        	   prepareForNextOrder(OrderID, remainingOrderItems);
        	   return;
           }

        }


	/**Generates a new order. The HashMap is used for storing an
	 * Order's item Name and the desired Quantity
         * @param OrderID,
         * @param itemAndQty :A Map with Item names as Key and
         * the quantity desired by the Customer as it's value
         * @param address of where the order needs to be delivered.
         *
	 */

	public void generateOrder (int OrderID, HashMap<String,Integer> itemAndQty, String address){
		int OrderIDcpy = OrderID;
		order CustomerOrder = new order (OrderID, itemAndQty, address);
		 //if (verifyOrderItems == true){ // then proceed with generating new Order.
		//if(verifyOrderItems(CustomerOrder)==true){
			initialOrders.put(OrderIDcpy, CustomerOrder); //HashMap contains all the Info on Orders
			ordersQueue.offer(OrderIDcpy); //This new order is on a queue
		//}
		//else{
			//System.out.println("Required Items and Quantities not present for the OrderID: " + OrderIDcpy);
		//}
	}


	//order reaches the exit/truck
        /**
         *
         * @param OrderID to check if a certain order has been
         * fulfilled
         */
	public void orderFulfilled(int OrderID){
		int exitOrderID = ordersQueue.poll(); //dequeue
		order details = initialOrders.get(exitOrderID);//update the order status.
		details.updateStatus("Fulfilled"); //the customer order has been fulfilled
		//initialOrders.remove(OrderID);

		/*We can remove the mapping of that OrderID
		 * once it's fulfilled but it could be
		 * useful when dealing with wrong orders,
		 * returns or damaged goods.
		 */
	}

	//Boolean method to check if Order items are present in Inventory
        /**
         *
         * @param customerOrder An order object used here to
         * verify if the Customer's ordered items are present in the
         * warehouse Inventory.
         * @return bothPresent If the item and quantity desired are
         * available in the warehouse Inventory.
         */
	public boolean verifyOrderItems (order customerOrder){
		boolean checkExistence=false;
		boolean itemAndQtyPresent = true; //This is TRUE if both items and quantities are present.
		String[] items = customerOrder.getArrayOfItemNames();
		Integer[] qty = customerOrder.getItemQty();
		int listLength = customerOrder.getNumberOfItems();
		for (int i=0; i<listLength; i++){

			/*Using for loop to check if item & quantity Present.
			 * ALL ITEMS & Quantity HAVE TO BE PRESENT for 
			 * itemAndQtyPresent to be TRUE
			 */
			checkExistence = I.checkExistWithQty(items[i],qty[i]);
			itemAndQtyPresent = checkExistence && itemAndQtyPresent;	
		}
		return itemAndQtyPresent;
	}
	
	/**
	 * @author Chaitanya Kovuri
	 * @param customerOrder
	 * @param currentItemIndex
	 * @return Point, i.e. Shelf Location for the wanted item
	 * so we can use that Point as an argument for RobotScheduler.
	 */
	
	public Point itemShelfLoc(order customerOrder, int currentItemIndex){
		String[] items = customerOrder.getArrayOfItemNames();
		String itemName = items[currentItemIndex];
		Point fetchShelf;
		fetchShelf = I.getshelfPosition(itemName);
		return fetchShelf;
	}
	
	//Point destination;

	public void prepareForNextOrder(int nextOrderID, HashMap<Integer, Integer>remainingOrderItems){
		order customerOrder = initialOrders.get(nextOrderID);
		customerOrder.updateStatus("In Progress");
		int orderSize = customerOrder.getNumberOfItems();
		remainingOrderItems.put(nextOrderID, orderSize);
	}
	

	
}

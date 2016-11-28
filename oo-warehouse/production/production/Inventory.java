package production;
/**
 *
 * @author Fan Gao
 *
 */

//use "list1.txt" to test

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Inventory {
	boolean isExist;
	//boolean isInList;
	int amount;
        //the amount of the item
	List<Map<String, Object>> inventory = new ArrayList<Map<String, Object>>();
	//inventory with items in it, each item is a hashmap

    public Inventory(List inventory){
        this.inventory = inventory;
    }


     /*This method read a txt file, with items information
      *then put into an arraylist, each element is an item
      *each item has name, amount, existence, shelf, point,...blablabla...
      *
      * @param nothing, read the file and acquire the inventory information
      *
      */
    public void data(){
    	BufferedReader br = null;
		try
		{      //read file from the path, when testing change path to "list1.txt"
			br = new BufferedReader(new FileReader("C:\\Users\\fgao6\\Desktop\\list1.txt"));
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
			return;
		}

		String[] columnName =
		{ "Id", "Name", "Amount", "Shelf#", "Position"};  //name of columns
		int i, index;
		String line;
		try
		{
			br.readLine(); //ignore first line
			while ((line = br.readLine()) != null)
			{
				index = 0;
				String[] se = line.split(" ");
				Map<String, Object> item = new HashMap<String, Object>();
				for (i = 0; i < se.length; i++)
				{
					if ("".equals(se[i]))
					{
						continue;
					}
					if (index >= columnName.length)
					{
						continue;
					}
					item.put(columnName[index], se[i]);
                                        //put item information into each item
					index++;
				}

				int amount = Integer.parseInt((String) item
							.get(columnName[2]));
				if (amount > 0){
					item.put("Existence", "Y");
				}
				else{
					item.put("Existence", "N");
				}
				inventory.add(item);
			}
			br.close();  //done read file

			outPutFile();  //out put the file
		} catch (IOException e)
		{
			e.printStackTrace();
		}
    }
	
   /**
     * use Point class to get the position of the item,
     * input the itemName, then get the position
     * will  return the position as a point
     * 
     * @param itemName
     * @return Point(position of the item)
     */
    public Point readPosition(String itemName){
    	int i;
    	String pos = "";
    	for (i = 0; i < inventory.size(); i++)
		{
    		Map<String, Object> newItem = new HashMap<String, Object>();
    		newItem = inventory.get(i);
			if (itemName.equals(newItem.get("Name").toString())){
				pos = newItem.get("Position").toString();
			}
		}
    	
    	String sx = (pos.split(","))[0];
    	String sy = (pos.split(","))[1];
    	
    	sx = sx.substring(1, sx.length());
    	sy = sy.substring(0, sy.length()-1);
    	
    	int x = Integer.parseInt(sx);
    	int y = Integer.parseInt(sy);
    	//System.out.println(x);        //!!!!! just for test, print integer of row and col
    	//System.out.println(y);
    	Point a = new Point(x,y);
    	return a;
    }


       /**
         * check existence with Qty
    	 * if exist isExist is true
    	 * else false
	 * @author Fan Gao
         * @param itemName
	 * @param Qty
         * @return boolean, if the item's Amount <= qty, return false; 
         *                  else, return true
         *
          * @author Chaitanya Kovuri
          * previous checkExist() method did not have Quantity as a parameter.
          * So I added it under a similar method to make sure I did not modify your
          * original code.
          * @param itemName is the Item you want to check in the inventory database.
          * @param Qty is the desired Quantity
          * Also an advice. I think using a HashMap<String, Integer> where the key is the
          * itemName and the VALUE= item's Quantity. This would make the check method
          * more efficient because you can just use something like HashMap.get("ItemName") 
          */
    public boolean checkExistWithQty(String itemName, int Qty){

    	for (int i = 0; i < inventory.size(); i++)
		{
    		Map<String, Object> newItem = new HashMap<String, Object>();
    		newItem = inventory.get(i);
			if (itemName.equals( newItem.get("Name").toString())){
				int a = Integer.parseInt((String) newItem.get("Amount"));
				if (a >= Qty){ //to check if Inventory has both the Item and Qty the customer wants.
					isExist = true;
					break;
				}
				else{
					isExist = false;
				}
			}
			else{
				isExist = false;  
			}
		}
    	return isExist;
    }

     
      /*This method can remove item from the inventory
       *first check if the item is in stock
       *if not exist, say not found
       *if exist, let the item's amount - qty
       *
       * @param itemName
       * @param Qty
       * its gonna remove the item is called, subtract by qty in Amount
       *
       */
    public void removeItem(String itemName, int Qty){
    	if (checkExistWithQty(itemName, Qty) == true){
    		int i;
    		//System.out.println(checkExist(itemName));
        	for (i = 0; i < inventory.size(); i++)
    		{
        		Map<String, Object> removedItem = new HashMap<String, Object>();
        		removedItem = inventory.get(i);
        		
    			if (itemName.equals(removedItem.get("Name").toString())){
    				int a = Integer.parseInt((String) removedItem.get("Amount"));
    				removedItem.put("Amount",a- Qty);
    				
    				if (a-Qty < 1){
    					removedItem.put("Existence","N");
    				}
    			}
    		}
    	}
    	else{
            System.out.println("Item "+ itemName + " is not avaible.");
            for (int i = 0; i < inventory.size(); i++)
    		{
        		Map<String, Object> removedItem = new HashMap<String, Object>();
        		removedItem = inventory.get(i);
        		
    			if (itemName.equals(removedItem.get("Name").toString())){
    			    System.out.println("Since item "+ itemName + "'s amout is not enough, try less amount.");  
                        }                  
                }
    	}
    	outPutFile();	
    }

	
     /*This method add item to inventory
      *if item already in inventory, amount + qty
      *if not in list, make new item id, let amount = qty
      *
      * @param itemName
      * @param Qty
      * its gonna add the called item into the inventory, plus the Amount by qty
      *
      */
  public void addItem(String itemName, int Qty){
    	boolean InList = false;
    	int i;
    	for (i = 0; i < inventory.size(); i++)
		{
    		Map<String, Object> newItem = new HashMap<String, Object>();
    		newItem = inventory.get(i);
			if (itemName.equals(newItem.get("Name").toString())){
				int a = Integer.parseInt((String) newItem
						.get("Amount"));
				newItem.put("Amount",a+Qty);
				newItem.put("Existence","Y");
				InList = true;
			}
		}
    	if (InList == false){
    		Map<String, Object> newItem = new HashMap<String, Object>();
    		newItem.put("Id", i+1);
    		newItem.put("Name",itemName);
    		newItem.put("Amount",Qty);
    		newItem.put("Existence","Y");
    		inventory.add(newItem);
    	}
    	
    	outPutFile();  
    } 

	
     /* This method write the modified inventory list into a new file.txt
      * output a new file with refreshed list
      *
      * @param nothing, but write all the "inventory" into a new file
      *
      */
    public void outPutFile(){
    	try
		{
    	   PrintWriter pw = new PrintWriter(new File("C:\\Users\\fgao6\\Desktop\\list2.txt"));
    	   pw.println("Id\tName\tAmount\tShelf#\tPosition\tExistence");
    	   String[] columnName = { "Id", "Name", "Amount", "Shelf#", "Position"};
			int cIndex;
			for (int i = 0; i < inventory.size(); i++)
			{
				Map<String, Object> st = inventory.get(i);
				cIndex = 0;
				pw.println(st.get(columnName[cIndex++]) + "\t"
				+ st.get(columnName[cIndex++]) + "\t"
				+ st.get(columnName[cIndex++]) + "\t"
                                + st.get(columnName[cIndex++]) + "\t"
                                + st.get(columnName[cIndex++]) + "\t"+"\t"
                                + st.get("Existence"))  ;
			}
			pw.flush();
			pw.close();

		} catch (IOException e)
		{
			e.printStackTrace();
		}
    }

    /*main method to test for inventory
     *
     * @param args
     */
    public static void main(String[] args) {

	List<Map<String, Object>> listA = new ArrayList<Map<String, Object>>();
	 //add code here to read file and insert the item in to listA

	Inventory a = new Inventory(listA);
	a.data();

	a.addItem("Z",3);
	a.addItem("A",5);
	a.addItem("F",16);
	a.removeItem("K",4);
        a.removeItem("Shoe", 2);
        a.readPosition("A");
        a.readPosition("G");
	}
}

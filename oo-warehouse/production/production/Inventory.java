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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Inventory {
    int capacity;
	boolean isExist;
	//boolean isInList;
	int amount;
        //the amount of the item
	List<Map<String, Object>> inventory = new ArrayList<Map<String, Object>>();
    Map<Integer, Integer> shelfinfo = new HashMap<Integer, Integer>();
        //List<Map<Integer,Integer>> shelflist = new ArrayList<Map<Integer, Integer>>();
    HashMap<Integer, int[]> shelfposition;
        
    public Inventory(List inventory){
        //this.shelflist = new ArrayList;
    	//Master.master.subscribe(this);
        this.inventory = inventory;
    }


    /**This method read a txt file, with items information
      *then put into an arraylist, each element is an item
      *each item has name, amount, existence, shelf, point,...blablabla...
      *
      * @param nothing, read the file and acquire the inventory information
      *
      */
    public void data(){
    	BufferedReader br = null;
    	//shelfposition.put(1,());
		try
		{      //read file from the path, when testing change path to "list1.txt"
			br = new BufferedReader(new FileReader("production/list1.txt"));
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
     * This method read the shelf id and each shelf's remaining capacity(50 - current item amount)
     * then put the id and its amount into a Hashmap
     * 
     * for example, the "shelfinfo" will be a Map like 
     * {1=5, 2=10, 3=40, 4=0, 5=5, 6=28, 7=5, 8=15, 9=10, 10=30, 11=20, 12=5, 13=45, 14=30, 15=15}
     */
    public Map<Integer, Integer> readshelf(){

        int i;
        int shelfid =1;
    	int amount=0;
        //Map<Integer, Integer> shelfinfo = new HashMap<Integer, Integer>();
    	for (i = 0; i < inventory.size(); i++)
		{
    		Map<String, Object> newItem = new HashMap<String, Object>();
                
    		newItem = inventory.get(i);
                int id = Integer.parseInt((String) newItem.get("Shelf#"));
                int a =Integer.parseInt((String) newItem.get("Amount"));
			if (shelfid==id){
			    amount = amount + a;
                            //System.out.println(amount);
			}
                        else{
                            shelfinfo.put(shelfid,50-amount);
                            amount =a;
                            shelfid=shelfid+1;
                            //amount = 0;
                            //i = i-1;
                        }
		}
        shelfinfo.put(shelfid, 50-amount); //put in the last shelf info
        //System.out.println(shelfinfo);
        return shelfinfo;
    }
            
            
    /**
     * This method gives shelf id by inputing item name.
     *
     * @param itemName
     * @return
     */
    public int readPosition(String itemName){
    	int i;
    	//String readpos = "";
    	int a = 0;
    	for (i = 0; i < inventory.size(); i++)
		{
    		Map<String, Object> newItem = new HashMap<String, Object>();
    		newItem = inventory.get(i);
			if (itemName.equals(newItem.get("Name").toString())){
				  a = Integer.parseInt((String) newItem
						.get("Shelf#"));
			}
		}
    	System.out.println(a);
    	return a;

    }
            
   /**
     * use Point class to get the position of the item,
     * input the itemName, then get the position
     * will  return the position as a point
     * 
     * @param itemName
     * @return Point(position of the item)
     */
    public Point getshelfPosition(String itemName){

    	int i;
    	String pos = "";
    	for (i = 0; i < inventory.size(); i++)
		{
    		Map<String, Object> newItem = new HashMap<String, Object>();
    		newItem = inventory.get(i);
			if (itemName.equals(newItem.get("Name").toString())){
				if (newItem.get("Position") != null) {
				  pos = newItem.get("Position").toString();
				  }
				
				else{
					  pos ="(3,3)";			
                                   }
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

     
     /**This method can remove item from the inventory
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
    				removedItem.put("Amount", a- Qty);
    				
    				if (a-Qty < 1){
    					removedItem.put("Existence","N");
    				}
    			}
    		}
        	//remove request
        	//int[] li = {2,3,4,5};
        	//RobotScheduler.addRequest(li);
        	
    	}
    	else{
            System.out.println("Item "+ itemName + " is not avaible.");
            for (int i = 0; i < inventory.size(); i++)
    		{
        		Map<String, Object> removedItem = new HashMap<String, Object>();
        		removedItem = inventory.get(i);
        		
    			if (itemName.equals(removedItem.get("Name").toString())){
    			    System.out.println("Since item "+ itemName + "'s amount is not enough, try less amount.");  
                        }                  
                }
    	}
    	outPutFile();	
    }

	
    /**This method add item to inventory
      *if item already in inventory, amount + qty
      *if not in list, make new item id, let amount = qty
      * //new added item//
      * It will find the largest capacity remaining in the in-using shelves
      *     if Qty < largest capacity remaining
      *             the new add item will go to that shelf
      *     else  
      *             the new item will go to a new(empty) shelf
      *
      * @param itemName
      * @param Qty
      * 
      * if the item already in the list, its gonna add the called item into the inventory, plus the Amount by qty
      *
      */
  public void addItem(String itemName, int Qty){
    	boolean InList = false;
    	int i;
        List<Map<String, Object>> listA = new ArrayList<>();
	Inventory b = new Inventory(listA);
        b.data();
	//b.readshelf();
        int max =0;
        int lastshelf =0;
        
        //get the max capacity of in-used shelves
        for (Map.Entry<Integer, Integer> m :b.readshelf().entrySet())  {  
           if ( m.getValue()> max){
                max = m.getValue();
           }  
        }
        //System.out.println(max);
        
        //get the last shelf in using
        for (Map.Entry<Integer, Integer> m :b.readshelf().entrySet())  {  
           if ( m.getKey()> lastshelf){
                lastshelf = m.getKey();
           }  
        }
        
        //get the shelf id with max capacity
        int keys=0;  
        Iterator it = b.readshelf().entrySet().iterator();  
        while (it.hasNext()) {  
            Map.Entry entry = (Entry) it.next();  
            int val = (int) entry.getValue();  
            if (val == max) {  
                keys= (int) entry.getKey();  
            }  
        }
  
               
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
                if (Qty <= max){
                    newItem.put("Shelf#",keys);
                    //newItem.put("Position", "("+ readPosition(itemName).row+","+readPosition(itemName).col+")");
                }
                else{
                    newItem.put("Shelf#",lastshelf+1);
                }
    		inventory.add(newItem);
    	}
    	
    	outPutFile();  
    } 

	
    /** This method write the modified inventory list into a new file.txt
      * output a new file with refreshed list
      *
      * @param nothing, but write all the "inventory" into a new file
      *
      */
    public void outPutFile(){
    	try
		{   
    		
    		//check test of inventory in list2.txt
    	   PrintWriter pw = new PrintWriter(new File("production\\list2.txt"));
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

   /**main method to test for inventory
     *
     * @param args
     */
    public static void main(String[] args) {

	List<Map<String, Object>> listA = new ArrayList<>();
	 //add code here to read file and insert the item in to listA

	Inventory a = new Inventory(listA);
	a.data();
        
        
	a.addItem("Z",25);
	a.addItem("A",5);
	a.addItem("F",16);
        a.addItem("Ball",48);
        
	a.removeItem("K",10);
        a.removeItem("Shoe", 2);
        a.getshelfPosition("A");
        a.getshelfPosition("G");
         a.readPosition("C");
        
	}

/*
public void populate() {
	for(String s:"ABCDEFGHIJKLMNOPQRSTUVWXYZ".split("")){
		System.out.println(s);
		addItem(s,50);
	}
}
*/
}

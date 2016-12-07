package production;

import java.util.HashMap;

/*
 * @author Cole Petersen
 * 
 * Shelf object:
 * Represents shelves themselves.
 */

public class Shelf {
	
	int id;
	int HomeRow;
	int HomeCol;
	int CurRow;
	int CurCol;
	int capacity;
	int AmtItems;
	
	// HashMap ItemsContained with key ItemID and value ItemAmt
	public HashMap<Integer, Integer> ItemsContained = new HashMap<Integer, Integer>();
	
	boolean BeingCarried;
	
	public Shelf(int id, int HomeRow, int HomeCol){
		
		this.id = id;
		this.HomeRow = HomeRow;
		this.HomeCol = HomeCol;
		this.CurRow = HomeRow;
		this.CurCol = HomeCol;
		this.capacity = 50;
		this.AmtItems = 0;
		
		this.BeingCarried = false;
		
		// We will have to decide how to organize items on shelves.
		// Each shelf will have a capacity, and this class will work with
		// Inventory to organize what goes where.
		
	}
	
	// Add Item(s) to shelf
	void AddItem(int ItemID, int ItemAmt){
		
		// Check that addition won't put shelf over capacity
		if(this.AmtItems + ItemAmt > 50){
			System.out.println("Over capacity. Choose a different shelf.");
			throw new IllegalArgumentException(Integer.toString(ItemAmt));
		}
		
		int CurAmt = this.ItemsContained.get(ItemID);
		this.ItemsContained.put(ItemID, ItemAmt + CurAmt); // Do addition
		this.AmtItems = this.AmtItems + ItemAmt; // Update amount of items on shelf
	}
	
	// Remove items from shelf
	void RemoveItems(int ItemID, int ItemAmt){
		
		int CurAmt = this.ItemsContained.get(ItemID);
		
		// Check that there are enough items
				if(CurAmt - ItemAmt < 0){
					System.out.println("Not enough items.");
					throw new IllegalArgumentException(Integer.toString(ItemAmt));
				}
		
		this.ItemsContained.put(ItemID, CurAmt - ItemAmt); // Do subtraction
		this.AmtItems = this.AmtItems - ItemAmt; // Update amount of items on shelf
	}
	
	// Get current shelf location (string)
	String getCurShelfLocation(){
		String row = Integer.toString(this.CurRow);
		String col = Integer.toString(this.CurCol);
		String loc = (row + ", " + col);
		return loc;
	}
	
	// Get home shelf location (string)
	String getHomeShelfLocation(){
		String row = Integer.toString(this.HomeRow);
		String col = Integer.toString(this.HomeCol);
		String loc = (row + ", " + col);
		return loc;
	}
	
	// Get shelf id
	int getShelfID(){
		return this.id;
	}
	
	// Get items contained on shelf
	HashMap<Integer, Integer> getItemsContained(){
		return this.ItemsContained;
	}
	
}

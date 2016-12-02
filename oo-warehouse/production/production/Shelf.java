package production;

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

}

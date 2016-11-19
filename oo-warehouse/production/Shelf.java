package production;

/*
 * @author Cole Petersen
 * 
 * Shelf object:
 * Represents shelves themselves.
 */

public class Shelf {
	
	int HomeRow;
	int HomeCol;
	int CurRow;
	int CurCol;
	
	boolean BeingCarried;
	
	public Shelf(int HomeRow, int HomeCol){
		
		this.HomeRow = HomeRow;
		this.HomeCol = HomeCol;
		this.CurRow = HomeRow;
		this.CurCol = HomeCol;
		
		this.BeingCarried = false;
		
		// We will have to decide how to organize items on shelves.
		// Each shelf will have a capacity, and this class will work with
		// Inventory to organize what goes where.
		
	}

}

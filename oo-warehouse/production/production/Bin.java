package production;
/**
 * 
 * @author rpolley
 * Bin full with Items Ordered
 * and available to push on the belt
 */

public class Bin {
	int pos;
	MockBelt on;//the belt this bin is on
	//todo: way to hold items
	public Bin(MockBelt b){
		pos = 0;
		on = b;
	}
	public boolean atStart(){
		return pos==0;
	}
	public boolean atEnd(){
		return pos==on.beltarea.length;
	}
	public BeltSpace getPosition(){
		return on.beltarea[pos];
	}
	/*
	 * @author rpolley
	 * move the bin down the belt
	 */
	public void move(){
		pos++;
	}
}

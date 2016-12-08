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
		on = b;
		pos = on.beltarea.length-1;
	}
	public boolean atStart(){
		return pos==on.beltarea.length-1;
	}
	public boolean atEnd(){
		return  pos==0;
	}
	public BeltSpace getPosition(){
		return on.beltarea[pos];
	}
	/*
	 * @author rpolley
	 * move the bin down the belt
	 */
	public void move(){
		if(pos > 0){
			pos--;
		}else{
			on.getBin();
			System.out.println("Got Bin!");
		}
	}
}

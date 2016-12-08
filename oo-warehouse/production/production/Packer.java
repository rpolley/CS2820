package production;

import java.awt.Point;
import java.util.ArrayList;

/**
 * 
 * @author Anani
 *
 */
public class Packer extends PackerSpace{ 
  	public  ArrayList<Point> beltarea; 
 	Floor F; 
 	public Packer(ArrayList<Point> beltarea, int row, int col){ 
  		super(row, col); 
 		this.beltarea = beltarea; 
 	} 
 	public void processItems() { 
 		Cell c = Cell(F.getPackLcs()); 
 		Object o = c.getContents();  // get what the Cell has in it 
 	 	assert o instanceof Bin;     // it had better be a Bin 
 	 	Bin b = (Bin)o;              // use the Bin to 
 	 	order v = b.getOrder();      // get the finished Order 
 	 	Parcel n = new Parcel(v.getAddress(),v.getOrderItems()); 
 	 	c.setContents(n);  // replace Bin with Parcel on the belt 

 	} 
 
 
 } 

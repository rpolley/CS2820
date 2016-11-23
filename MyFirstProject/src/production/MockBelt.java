package production;

import java.awt.List;
import java.awt.Point;

/**
 * 
 * @author Anani
 *
 */
public class MockBelt implements Belt {
	
	Floor n;	
	List beltarea;
	Bin madeBin;
	
public MockBelt(Floor n){
	this.n = n;
	beltarea = n.getBeltArea();
	madeBin = null;	
}


public void tick(int count){
	
	if (madeBin!= null){
		if (!madeBin.isFinished())return;
		Cell c = n.getCell(n.getPacker());
		if (c.getContents()!=null)return;
		c.setContents(madeBin);
		madeBin = null;		
	}
	if (!isMovable())return;
	
}
private boolean isMovable(){
	
	if (madeBin != null)return false;	
	for (Point p: beltarea){
		Cell c= n.getCell(p);
		Object o  = c.getContents();
		if (o==null);
		if ((o instanceof Bin) && !((Bin)o).isFinished())return false;
		if ((o instanceof Parcel) && !((Parcel)o).isFinished())return false;
	}
	return true;
}

private void doPacker(){
	Cell c = n.getCell(n.getPacker());
	Object o = c.getContents();
	assert o instanceof Bin;
	Bin b = (Bin)o;
	order v = b.getOrder();
	Parcel m = new Parcel(v.getAddress(), v.getOrderItems());
	c.setContents(m);
}

public boolean binAvailable() {
	if(madeBin != null){
	return false;
	}
Cell c = n.getCell((n.picker));
if (c.getCell()!=null)	return false;
return true;
}

public Bin getBin(){
	assert madeBin==null;
	madeBin = new Bin();
	return madeBin;
	}	

}

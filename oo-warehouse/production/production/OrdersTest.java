package production;
import org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.*;

public class OrdersTest{
	
    HashMap<String,Integer> order1;
    HashMap<String,Integer> order2;
  

    @Before
    public void setUp(){
    	
        order1 = new HashMap<>();
        order2 = new HashMap<>();
        order1.put("iphone6", 4);
        order1.put("ipadPro", 10);
        order1.put("pen", 90);
        order2.put("television", 14);
        order2.put("ipadPro", 109);
        order2.put("board", 2);
    }
    
    @Test
    public void testOne(){
        Orders testing = new Orders();
        testing.generateOrder(3, order1, "Clinton St");
	    testing.generateOrder(9, order2, "Burlington St");
    }
    
}

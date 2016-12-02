package production;

import javax.swing.*;
import javax.swing.text.html.HTMLDocument.Iterator;
import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.*;

public class Visualizer {
    /**
     * @wbp.parser.entryPoint
     */
	//private static final String floorFile = "res/Floor.txt";
	//private static BufferedReader floorReader = readFloor();
	private static Object[][] floor;
	private static HashMap<Robot,int[]> robots;
	private static HashMap<Integer,int[]> shelves;
	private static HashMap<int[],JLabel> labelGrid;
	private static HashMap<int[],JLabel> initialSetup;
	private static HashMap<Robot, int[]> oldRobots;
	private static HashMap<Integer,int[]> oldShelves;
	private static final ImageIcon beltIcon = new ImageIcon("res/Belt.png");
	private static final ImageIcon floorTileIcon = new ImageIcon("res/Highway.png");
	private static final ImageIcon highwayIcon = new ImageIcon("res/Highway.png");
	private static final ImageIcon pickerIcon = new ImageIcon("res/Picker.png");
	private static final ImageIcon packerIcon = new ImageIcon("res/Packer.png");
	private static final ImageIcon chargerIcon = new ImageIcon("res/Charger.png");
	private static final ImageIcon shelfZoneIcon = new ImageIcon("res/ShelfZone.png");
	private static final ImageIcon robotIcon = new ImageIcon("res/Robot.png");
	private static final ImageIcon robotShelfIcon = new ImageIcon("res/Robot.png");
	private static final ImageIcon shelfIcon = new ImageIcon("res/Shelf.png");
	private static JFrame window;
	private static JPanel panel; 
	
	public Visualizer(){
		
		floor = Master.master.getFloor().layout;
		robots = Master.master.getRobotScheduler().RobotLocs;
		shelves = Master.master.getRobotScheduler().ShelvesLocs;
		labelGrid = new HashMap<int[],JLabel>();
		initialSetup = new HashMap<int[],JLabel>();
		oldRobots = (HashMap<Robot, int[]>) robots.clone();
		oldShelves = (HashMap<Integer, int[]>) shelves.clone();
		
		window = new JFrame("Warehouse Visualizer");
	    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        
	    panel = new JPanel();
	    window.getContentPane().add(panel, BorderLayout.CENTER);
	    panel.setLayout(new GridLayout(floor.length,floor[0].length));
	     
	    initialize();
	     
	    window.pack();
	    window.setVisible(true);
	}
	
	/**
	 * 
	 * @author James Vipond
	 * @result Reads information from the Floor class to
	 * create the layout of the unmovable components of the floor
	 */
    private static void initialize() { 
    	
    	for(int x = 0; x < floor.length; x++){
        	for(int y = 0; y < floor[0].length; y++){
        		Object here = floor[x][y];
        		int[] coordinates = {x,y};
        		if (here instanceof FloorSpace){
        			labelGrid.put(coordinates,new JLabel(highwayIcon));
    				panel.add(labelGrid.get(coordinates));
    				continue;
        		}else if(here instanceof MockBelt){
        			labelGrid.put(coordinates,new JLabel(beltIcon));
    				panel.add(labelGrid.get(coordinates));
    				continue;
        		}else if(here instanceof Charger){
        			labelGrid.put(coordinates,new JLabel(chargerIcon));
    				panel.add(labelGrid.get(coordinates));
    				continue;
        		}else if(here instanceof Pick){
        			labelGrid.put(coordinates,new JLabel(pickerIcon));
    				panel.add(labelGrid.get(coordinates));
    				continue;
        		}else if(here instanceof Pack){
        			labelGrid.put(coordinates,new JLabel(packerIcon));
    				panel.add(labelGrid.get(coordinates));
    				continue;
        		}else if(here instanceof ShelfSpace){
        			labelGrid.put(coordinates,new JLabel(shelfZoneIcon));
    				panel.add(labelGrid.get(coordinates));
    				continue;
        		}else{
        			labelGrid.put(coordinates,new JLabel(floorTileIcon));
    				panel.add(labelGrid.get(coordinates));
    				continue;
        		}
        	}
        }
    	
/*        for(int x = 0; x < floor.length; x++){
        	for(int y = 0; y < floor[0].length; y++){
        		Object here = floor[x][y];
        		switch(here.getClass()){
        			case (new Belt().getClass()):
        				labelGrid.put({x,y},newJLabel(beltIcon))
        				panel.add(labelGrid.get({x,y}));
        				continue;
        			case (new Highway().getClass()):
        				labelGrid.put({x,y},newJLabel(highwayIcon))
        				panel.add(labelGrid.get({x,y}));
        				continue;
        			case (new Charger().getClass()):
        				labelGrid.put({x,y},newJLabel(chargerIcon))
        				panel.add(labelGrid.get({x,y}));
        				continue;
        			case (new Pick().getClass()):
        				labelGrid.put({x,y},newJLabel(pickerIcon))
        				panel.add(labelGrid.get({x,y}));
        				continue;
        			case (new Pack().getClass()):
        				labelGrid.put({x,y},newJLabel(packerIcon))
        				panel.add(labelGrid.get({x,y}));
        				continue;
        			case (new ShelfSpace.getClass()):
        				labelGrid.put({x,y},newJLabel(shelfZoneIcon))
        				panel.add(labelGrid.get({x,y}));
        				continue;
        		}
        	}
        }
*/        
        initialSetup = (HashMap<int[], JLabel>) labelGrid.clone();
    }
 
    
    
    /**
    *
    * @author James Vipond
    * @result Renders the new locaitons of robots and shelves
    */
    private static void placeMovables(){
    	
    	java.util.Iterator<Robot> oldRobotIter = oldRobots.keySet().iterator();
    	java.util.Iterator<Integer> oldShelfIter =  oldShelves.keySet().iterator();
    	
    	/*
    	while(((java.util.Iterator<Integer>) oldShelfIter).hasNext()){
    		Integer oldShelf = oldShelfIter.next();
    		labelGrid.get(oldShelves.get(oldShelf)).setIcon(initialSetup.get(oldShelf).getIcon());
    	}
    	*/
    	
    	while(((java.util.Iterator<Robot>) oldRobotIter).hasNext()){
    		Robot oldRobot = oldRobotIter.next();
    		int[] coordinates = {oldRobot.row,oldRobot.col};
    		int[] coordinates = {oldRobot.getRow(),oldRobot.getCol()};
    		labelGrid.get(coordinates).setIcon(initialSetup.get(coordinates).getIcon());    		
    	}
    	
    	java.util.Iterator<Robot> robotIter = robots.keySet().iterator();
    	java.util.Iterator<Integer> shelfIter = shelves.keySet().iterator();
    	
    	/*
    	while(shelfIter.hasNext()){
    		Integer shelf = shelfIter.next();
    		labelGrid.get(shelves.get(shelf)).setIcon(shelfIcon);
    	}
    	*/
    	
    	while(robotIter.hasNext()){
    		Robot robot = robotIter.next();
    		int[] coordinates = {robot.row,robot.col};
    		int[] coordinates = {robot.getRow(),robot.getCol()};
    		if (robot.isCarryingShelves()){
    			labelGrid.get(coordinates).setIcon(robotShelfIcon);
    		}else{
    			labelGrid.get(coordinates).setIcon(robotIcon);	
    		}
    	}
    	
    	oldRobots = (HashMap<Robot, int[]>) robots.clone();
    	oldShelves = (HashMap<Integer, int[]>) shelves.clone();
    	
    }
    
    /**
    *
    * @author James Vipond
    * @result Called every frame to update display with
    * new information
    */
    public void onFrame(){
    	placeMovables();
    }
}

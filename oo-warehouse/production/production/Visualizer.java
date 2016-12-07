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
	private static Floor floor;
	private static Object[][] floorLayout;
	public static int tickNumber;
	private static HashMap<Robot,int[]> robots;
	private static ArrayList<Shelf> shelves;
	private static HashMap<int[],JLabel> labelGrid;
	private static HashMap<int[],JLabel> initialSetup;
	private static HashMap<Robot, int[]> oldRobots;
	private static ArrayList<Shelf> oldShelves;
	private static ArrayList<int[]> floorCoords;
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
	private static final ImageIcon emptyBinIcon = new ImageIcon("res/EmptyBin.png"); //new icons
	private static final ImageIcon fullBinIcon = new ImageIcon("res/FullBin.png");
	private static final ImageIcon packageIcon = new ImageIcon("res/Package.png");
	private static JFrame window;
	private static JPanel panel; 
	
	
	public Visualizer(){
		
		tickNumber = 0;
		floor = Master.master.getFloor();
		floorLayout = floor.layout;
		floorCoords = new ArrayList<int[]>();
		robots = Master.master.getRobotScheduler().RobotLocs;
		shelves = floor.ShelfList;
		labelGrid = new HashMap<int[],JLabel>();
		initialSetup = new HashMap<int[],JLabel>();
		oldRobots = (HashMap<Robot, int[]>) robots.clone();
		oldShelves = (ArrayList<Shelf>) shelves.clone();
		
		window = new JFrame("Warehouse Visualizer");
	    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        
	    panel = new JPanel();
	    window.getContentPane().add(panel, BorderLayout.CENTER);
	    panel.setLayout(new GridLayout(floorLayout.length,floorLayout[0].length));
	     
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
    	
    	for(int x = 0; x < floorLayout.length; x++){
        	for(int y = 0; y < floorLayout[0].length; y++){
        		Object here = floorLayout[x][y];
        		int[] coordinates = {x,y};
        		floorCoords.add(coordinates);
        		if (here instanceof FloorSpace){
        			labelGrid.put(coordinates,new JLabel(floorTileIcon));
        			initialSetup.put(coordinates,new JLabel(floorTileIcon));
    				panel.add(labelGrid.get(coordinates));
    				continue;
        		}else if(here instanceof BeltSpace){
        			labelGrid.put(coordinates,new JLabel(beltIcon));
        			initialSetup.put(coordinates,new JLabel(beltIcon));
    				panel.add(labelGrid.get(coordinates));
    				continue;
        		}else if(here instanceof Charger){
        			labelGrid.put(coordinates,new JLabel(chargerIcon));
        			initialSetup.put(coordinates,new JLabel(chargerIcon));
    				panel.add(labelGrid.get(coordinates));
    				continue;
        		}else if(here instanceof Pick){
        			labelGrid.put(coordinates,new JLabel(pickerIcon));
        			initialSetup.put(coordinates,new JLabel(pickerIcon));
    				panel.add(labelGrid.get(coordinates));
    				continue;
        		}else if(here instanceof Pack){
        			labelGrid.put(coordinates,new JLabel(packerIcon));
        			initialSetup.put(coordinates,new JLabel(packerIcon));
    				panel.add(labelGrid.get(coordinates));
    				continue;
        		}else if(here instanceof ShelfSpace){
        			labelGrid.put(coordinates,new JLabel(shelfZoneIcon));
        			initialSetup.put(coordinates,new JLabel(shelfZoneIcon));
    				panel.add(labelGrid.get(coordinates));
    				continue;
        		}else{
        			labelGrid.put(coordinates,new JLabel(floorTileIcon));
        			initialSetup.put(coordinates,new JLabel(floorTileIcon));
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
    * @result Updates Visualizer variables with the new positions of robots and shelves
    */
    private static void updatePositions(){
    	robots = Master.master.getRobotScheduler().RobotLocs;
		shelves = floor.ShelfList;
    
    }
    
    
    /**
    *
    * @author James Vipond
    * @result Renders the new locaitons of robots and shelves
    */
    private static void placeMovables(){
    	
    	java.util.Iterator<Robot> oldRobotIter = oldRobots.keySet().iterator();
    	
    	
    	for(Shelf oldShelf : oldShelves){
    		int[] coordinates = {oldShelf.CurRow,oldShelf.CurCol};
    		for(int[] c : floorCoords){
    			if(Arrays.equals(coordinates,c)){
    				JLabel oldLabel = labelGrid.get(c);
    	    		JLabel originalLabel = initialSetup.get(c);
    	    		oldLabel.setIcon(originalLabel.getIcon());
    			}
    		}
    	}
    	
    	
    	//This block reverts the icon at the location of the robot before this tick
    	//to the original icon that it was initialized to i.e. when the robot moves
    	//off of a shelf zone space, the icon changes from robotIcon to ShelfZoneIcon
    	while(((java.util.Iterator<Robot>) oldRobotIter).hasNext()){
    		Robot oldRobot = oldRobotIter.next();
    		int[] coordinates = {oldRobot.getRow(),oldRobot.getCol()};
    		for(int[] c : floorCoords){
    			if(Arrays.equals(coordinates,c)){
    				JLabel oldLabel = labelGrid.get(c);
    	    		JLabel originalLabel = initialSetup.get(c);
    	    		oldLabel.setIcon(originalLabel.getIcon());
    			}
    		}
    		//System.out.println(coordinates[0]);
    		//System.out.println(coordinates[1]);
    		
    		//System.out.println(oldLabel);
    		//System.out.println(originalLabel);
    		//labelGrid.get(coordinates).setIcon(initialSetup.get(coordinates).getIcon());
    		//System.out.println("Remove old robot");
    	}
    	
    	java.util.Iterator<Robot> robotIter = robots.keySet().iterator();
    	
    	/*
    	while(shelfIter.hasNext()){
    		Integer shelf = shelfIter.next();
    		labelGrid.get(shelves.get(shelf)).setIcon(shelfIcon);
    	}
    	*/
    	
    	for(Shelf s : shelves){
    		int[] shelfCoordinates = {s.CurRow,s.CurCol};
    		for(int[] c : floorCoords){
    			if(Arrays.equals(shelfCoordinates,c)){
    				if(s.CurRow == s.HomeRow && s.CurCol == s.HomeCol){
    				JLabel oldLabel = labelGrid.get(c);
    	    		oldLabel.setIcon(shelfIcon);
    	    		//System.out.println("Shelf at: [" + s.CurRow + "," + s.CurCol + "]" );
    				}
    			}
    		}
    	}
    	//This block changes the icon at the location that the robot moved to
    	//during this tick. It also checks if the robot is carrying a shelf.
    	while(robotIter.hasNext()){
    		Robot robot = robotIter.next();
    		int[] robotCoordinates = {robot.getRow(),robot.getCol()};
    		if (robot.isCarryingShelves()){
    			for(int[] c : floorCoords){
        			if(Arrays.equals(robotCoordinates,c)){
        				JLabel newLabel = labelGrid.get(c);
        	    		newLabel.setIcon(robotShelfIcon);
        			}
        		}
    			System.out.println("Robot with shelf at: [" + robotCoordinates[0] + "," + robotCoordinates[1] + "]");
    		}else{
    			for(int[] c : floorCoords){
        			if(Arrays.equals(robotCoordinates,c)){
        				JLabel newLabel = labelGrid.get(c);
        				newLabel.setIcon(robotIcon);
        			}
        		}
    			System.out.println("Robot without shelf at: [" + robotCoordinates[0] + "," + robotCoordinates[1] + "]");
    			
    		}
    	}
    	
    	
    	oldRobots = (HashMap<Robot, int[]>) robots.clone();
    	oldShelves = (ArrayList<Shelf>) shelves.clone();
    }
    
    /**
    *
    * @author James Vipond
    * @result Called every frame to update display with
    * new information
    */
    public void onFrame(){
    	tickNumber++;
    	System.out.println("Tick " + tickNumber);
    	updatePositions();
    	placeMovables();
    }
}

package production;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.HashMap;

public class MockVisualizer{
    /**
     * @wbp.parser.entryPoint
     */
	//private static final String floorFile = "res/Floor.txt";
	//private static BufferedReader floorReader = readFloor();
	private static Object[][] floor = Master.master.getFloor().layout;
	private static HashMap<Robot, int[]> robots = Master.master.getRobotScheduler().RobotLocs;
	private static HashMap<Integer,int[]> shelves = Master.master.getRobotScheduler().ShelvesLocs;
	private static final ImageIcon beltIcon = new ImageIcon("res/Belt.png");
	private static final ImageIcon highwayIcon = new ImageIcon("res/Highway.png");
	private static final ImageIcon pickerIcon = new ImageIcon("res/Picker.png");
	private static final ImageIcon packerIcon = new ImageIcon("res/Packer.png");
	private static final ImageIcon chargerIcon = new ImageIcon("res/Charger.png");
	private static final ImageIcon shelfZoneIcon = new ImageIcon("res/ShelfZone.png");
	private JFrame window;
	private JPanel panel; 
	
	public MockVisualizer(){
		 JFrame frame = new JFrame("Warehouse Visualizer");
	     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        
	     JPanel panel = new JPanel();
	     frame.getContentPane().add(panel, BorderLayout.CENTER);
	     panel.setLayout(new GridLayout(floor.length,floor[0].length));
	     
	     initialize();
	     
	     frame.pack();
	        frame.setVisible(true);
	}
	public FrameListener updater = new FrameListener(){
		public void onFrame(){
			
		}
	};
    private static void initialize() {       
        /*
        for(int x = 0; x < floor.length; x++){
        	for(int y = 0; y < floor[0].length; y++){
        		Object here = floor[x][y];
        		switch(here.getClass()){
        			case (new Belt().getClass()):
        				panel.add(new JLabel(beltIcon));
        				continue;
        			case (new Highway().getClass()):
        				panel.add(new JLabel(highwayIcon));
        				continue;
        			case (new Charger().getClass()):
        				panel.add(new JLabel(chargerIcon));
        				continue;
        			case (new Pick().getClass()):
        				panel.add(new JLabel(pickerIcon));
        				continue;
        			case (new Pack().getClass()):
        				panel.add(new JLabel(packerIcon));
        				continue;
        			case (new ShelfSpace.getClass()):
        				panel.add(new JLabel(shelfZoneIcon));
        				continue;
        		}
        	}
        	
        }   
        */  
    }    
}


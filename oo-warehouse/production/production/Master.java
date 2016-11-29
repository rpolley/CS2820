package production;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.List;
import java.util.Scanner;

public class Master {
	protected Master(int speed, int robotCount){
		subscribedListeners = new LinkedList<FrameListener>();
		this.speed = speed;
		this.robotCount = robotCount;
		this.time = 0;
		this.stopped = true;
	}
	//singleton
	//this is the main master simulation
	public static final Master master = new Master(1,1);
	
	private Collection<FrameListener> subscribedListeners;
	private RobotScheduler robots;
	private int robotCount;
	private MockBelt belts;
	private Inventory inventory;
	private Orders orders;
	private Floor floor;
	private MockVisualizer visualizer;
	private FrameListener visualizerUpdater;//used to make sure that visualizer draws at the end of the frame
	private List<Map<String,Object>> initialInventory;
	//speed of the simulation relative to real time
	//measured in fps
	//eg 1 indicates 1 second in simulation=1 second real time
	//and 60 would indicate 1 minute in simulation=1 second real time
	//0 indicates as fast as the computer can go
	private int speed;
	private int time;
	private boolean stopped;
	
	/*
	 * @author rpolley
	 */
	public RobotScheduler getRobotScheduler() {
		return robots;
	}

	/*
	 * @author rpolley
	 */
	public MockBelt getBelts() {
		return belts;
	}
	
	/*
	 * @author rpolley
	 */
	public Inventory getInventory() {
		return inventory;
	}

	/*
	 * @author rpolley
	 */
	public Orders getOrders() {
		return orders;
	}
	
	/*
	 * @author rpolley
	 */
	public Floor getFloor() {
		return floor;
	}
	
	/*
	 * @author rpolley
	 */
	public MockVisualizer getVisualizer() {
		return visualizer;
	}
	
	/*
	 * @author rpolley
	 * @return the current number of frames the simulation has been running
	 */
	public int getTime(){
		return time;
	}
	/*
	 * @author rpolley
	 * turns an ini file in form of
	 * arg1: val1
	 * arg2: val2
	 * arg3: val3
	 * into a map mapping args to vals
	 * @param the ini file
	 * @returns a map containing the arguments
	 */
	public static Map<String,String> parseIni(File f) throws FileNotFoundException{
		FileInputStream inFile = new FileInputStream(f);
		Scanner in = new Scanner(inFile);
		HashMap<String,String> args = new HashMap<String,String>();
		//iterate over the lines
		while(in.hasNextLine()){
			String pair = in.nextLine();
			String[] kv = pair.split(": ");
			args.put(kv[0], kv[1]);
		}
		return args;
	}
	/*
	 * @author rpolley
	 * used if visualizer wants to implement a way to stop the simulation from the ui
	 * this will cause the main loop to terminate after the call stack containing it resolves
	 */
	public void stop(){
		stopped = true;
		cleanupAll();
	}
	
	/*
	 * @author rpolley
	 * add a frame listener, so that every time a quantum of time passes it's
	 * onFrame method is called
	 */
	public void subscribe(FrameListener subscriber){
		subscribedListeners.add(subscriber);
	}
	/*
	 * @author rpolley
	 * simulate a quantum/frame of time
	 */
	private void runFrame(){
		for(FrameListener l : subscribedListeners){
			if(stopped)break;
			l.onFrame();
		}
		//update the visualizer after everything else has run
		//
		if(visualizerUpdater!=null){
			visualizerUpdater.onFrame();
		}
		time++;
	}
	/*
	 * @author rpolley
	 * allow modules to do one last thing before they exit
	 */
	private void cleanupAll(){
		for(FrameListener l : subscribedListeners){
			l.cleanup();
		}
	}
	/*
	 * @author rpolley
	 * initialize the simulation, most of the stuff here can't be done in the constructor,
	 * since some of the classes depend on an extant master object
	 */
	public void initializeSimulation(){
		this.initialInventory = new ArrayList();
		this.robots = new RobotScheduler();
		this.robots.addRobots();
		this.inventory = new Inventory(initialInventory);
		this.orders = new Orders();
		this.floor = new Floor(10,10,1);
		this.visualizer = new MockVisualizer();
		this.visualizerUpdater = this.visualizer.updater;
	}
	/*
	 * @author rpolley
	 * start the main simulation loop 
	 */
	public void startSimulation(){
		stopped = false;
		while(!stopped){
			long frameStart = System.currentTimeMillis();
			runFrame();
			long frameEnd = System.currentTimeMillis();
			long frameRuntime = frameEnd-frameStart;
			try {
				if(speed!=0&&1000/speed>frameEnd-frameStart)
					Thread.sleep(1000/speed-(frameRuntime));
			} catch (InterruptedException e) {
				//ignore the exception
				//we're not waiting to be woken up
				//so this exception shouldn't fire
				//and even if it does, it's not really important to handle
			}
		}
		
	}

}

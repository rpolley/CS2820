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
		//doesn't acutally affect the number of robots
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
	private Inventory inventory;
	private Orders orders;
	private Floor floor;
	//private MockVisualizer visualizer;
	private Visualizer visualizer;
	private List<Map<String,Object>> initialInventory;
	//speed of the simulation relative to real time
	//measured in fps
	//eg 1 indicates 1 second in simulation=1 second real time
	//and 60 would indicate 1 minute in simulation=1 second real time
	//0 indicates as fast as the computer can go
	private int speed;
	//how many frames have passed since the simulation started
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
	public Visualizer getVisualizer() {
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
		/*
		 * update the visualizer after everything else has run
		 *since the visualizer draws things based on their state when it's called
		 *if we call it in the middle of a frame
		 *it will not accurately reflect the state of the simulation
		 *at a single point of time
		 *so instead call it after everything else
		 **/
		if(visualizer!=null){
			visualizer.onFrame();
		}
		//update time
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
		//initialize  the robot scheduler
		//since it can add robots at any time, robots.addRobots needs to be called
		this.floor = new Floor(10,10,1);
		this.robots = new RobotScheduler();
		this.robots.addRobots();
		this.inventory = new Inventory(initialInventory);
		this.orders = new Orders();
		//initialize the floor in a 10 by 10 configuration with 1 robot
		this.visualizer = new Visualizer();
	}
	/*
	 * @author rpolley
	 * start the main simulation loop 
	 */
	public void startSimulation(){
		stopped = false;
		while(!stopped){
			/*
			 * run frame while regulating fps
			 * by timing how long it takes for each step to run,
			 * and then waiting out the rest of the time
			 */
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

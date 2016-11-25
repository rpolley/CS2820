package production;

public class Production {
	/*
	 * @author rpolley
	 * entry point for running the simulation
	 */
	public static void main(String[] args){
		Master.master.initializeSimulation();
		Master.master.startSimulation();
	}
}

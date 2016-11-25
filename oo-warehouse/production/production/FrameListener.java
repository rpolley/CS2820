package production;
/*
 * this interface should be implemented by any class wanting to keep track of the passage of time
 */
public interface FrameListener {
	/*
	 * called every time a amount of time equal to the amount of time a robot
	 * takes to move one grid square passes inside the warehouse simulation
	 * which will be referred to from now on as one quantum of time
	 */
	public void onFrame();
	/*
	 * called for every FrameListener object after the last frame occurs
	 * optional and does nothing by default
	 * if you want to save something to a file before the program exits, this is where you do it
	 */
	public default void cleanup(){
		//does nothing
		//ignore this method if you don't need it
	}
}

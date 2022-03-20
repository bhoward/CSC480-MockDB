package edu.depauw.csc480.mockdb.sim;

/**
 * Abstract base class for a simulation event. It keeps track of the simulation
 * time at which the event is supposed to occur.
 * 
 * @author bhoward
 */
public abstract class AbstractEvent implements Event {
	private double time;

	/**
	 * Construct a simulation event to occur at the given time.
	 * 
	 * @param time
	 */
	public AbstractEvent(double time) {
		this.time = time;
	}

	@Override
	public double getTime() {
		return time;
	}
}

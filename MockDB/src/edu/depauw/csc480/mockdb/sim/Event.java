package edu.depauw.csc480.mockdb.sim;

/**
 * An Event in a simulation. At the specified time, the perform method will be
 * called.
 * 
 * @author bhoward
 */
public interface Event {
	double getTime();

	void perform(EventLoop loop);
}

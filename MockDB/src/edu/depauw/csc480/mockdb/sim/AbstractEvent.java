package edu.depauw.csc480.mockdb.sim;

public abstract class AbstractEvent implements Event {
	private double time;

	public AbstractEvent(double time) {
		this.time = time;
	}

	@Override
	public double getTime() {
		return time;
	}
}

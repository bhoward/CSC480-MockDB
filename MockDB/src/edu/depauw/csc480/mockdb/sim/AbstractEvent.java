package edu.depauw.csc480.mockdb.sim;

public abstract class AbstractEvent implements Event {
	private int time;

	public AbstractEvent(int time) {
		this.time = time;
	}

	@Override
	public int getTime() {
		return time;
	}
}

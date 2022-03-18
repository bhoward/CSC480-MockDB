package edu.depauw.csc480.mockdb;

import edu.depauw.csc480.mockdb.sim.Event;
import edu.depauw.csc480.mockdb.sim.EventLoop;

public class DemoEvent implements Event {
	private int time;
	private String message;

	public DemoEvent(int time, String message) {
		this.time = time;
		this.message = message;
	}

	@Override
	public int getTime() {
		return time;
	}

	@Override
	public void perform(EventLoop loop) {
		System.out.println(message);
		if (time == 10) {
			loop.schedule(new DemoEvent(time + 5, "Simulated"));
		}
	}

}

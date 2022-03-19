package edu.depauw.csc480.mockdb.sim;

import java.util.PriorityQueue;

public class EventLoop {
	private PriorityQueue<Event> events;

	public EventLoop() {
		this.events = new PriorityQueue<>((e1, e2) -> Double.compare(e1.getTime(), e2.getTime()));
	}

	public void schedule(Event event) {
		events.add(event);
	}

	public void runUntil(double endTime) {
		while (!events.isEmpty()) {
			Event event = events.remove();
			if (event.getTime() > endTime) {
				break;
			}
			event.perform(this);
		}
	}
}

package edu.depauw.csc480.mockdb.sim;

import java.util.PriorityQueue;

public class EventLoop {
	private PriorityQueue<Event> events;

	public EventLoop() {
		this.events = new PriorityQueue<>((e1, e2) -> e1.getTime() - e2.getTime());
	}

	public void schedule(Event event) {
		events.add(event);
	}

	public void runUntil(int endTime) {
		while (!events.isEmpty()) {
			Event event = events.remove();
			if (event.getTime() > endTime) {
				break;
			}
			event.perform(this);
		}
	}
}

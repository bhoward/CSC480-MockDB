package edu.depauw.csc480.mockdb.sim;

import java.util.PriorityQueue;

/**
 * The main loop of the event-driven simulation. Keeps track of future scheduled
 * events and performs them in the correct order.
 * 
 * @author bhoward
 */
public class EventLoop {
	private PriorityQueue<Event> events;

	/**
	 * Construct an EventLoop with no Events initially scheduled.
	 */
	public EventLoop() {
		this.events = new PriorityQueue<>((e1, e2) -> Double.compare(e1.getTime(), e2.getTime()));
	}

	/**
	 * Add the given Event to the schedule.
	 * 
	 * @param event
	 */
	public void schedule(Event event) {
		events.add(event);
	}

	/**
	 * Run this EventLoop until the next available Event is past the given ending
	 * time.
	 * 
	 * @param endTime
	 */
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

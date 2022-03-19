package edu.depauw.csc480.mockdb;

import edu.depauw.csc480.mockdb.db.EntityManager;
import edu.depauw.csc480.mockdb.sim.CreateDepartmentEvent;
import edu.depauw.csc480.mockdb.sim.EventLoop;

public class Main {

	public static void main(String[] args) {
		EventLoop loop = new EventLoop();
		
		loop.schedule(new CreateDepartmentEvent(0, "Mathematics"));
		loop.schedule(new CreateDepartmentEvent(0, "English"));
		loop.schedule(new CreateDepartmentEvent(20, "Computer Science"));
		
		loop.runUntil(25);
		
		EntityManager em = EntityManager.getInstance();
		em.dump(System.out);
	}

}

package edu.depauw.csc480.mockdb;

import edu.depauw.csc480.mockdb.db.EntityManager;
import edu.depauw.csc480.mockdb.sim.Config;
import edu.depauw.csc480.mockdb.sim.CreateDepartmentEvent;
import edu.depauw.csc480.mockdb.sim.EventLoop;

public class Main {
	public static void main(String[] args) {
		EventLoop loop = new EventLoop();
		
		// Create a variety of departments over the years
		// Currently we can only add new departments, not remove them
		for (var p : Config.DEPARTMENT_START.entrySet()) {
			loop.schedule(new CreateDepartmentEvent(p.getValue(), p.getKey()));
		}
		
		loop.runUntil(Config.SIMULATION_YEARS);
		
		EntityManager em = EntityManager.getInstance();
		em.dump(System.out);
	}
}

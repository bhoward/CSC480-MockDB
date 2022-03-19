package edu.depauw.csc480.mockdb.sim;

import edu.depauw.csc480.mockdb.db.EntityManager;
import edu.depauw.csc480.mockdb.model.Department;
import edu.depauw.csc480.mockdb.model.Faculty;

public class HireFacultyEvent extends AbstractEvent implements Event {
	private Department department;

	public HireFacultyEvent(double time, Department department) {
		super(time);
		this.department = department;
	}

	@Override
	public void perform(EventLoop loop) {
		EntityManager em = EntityManager.getInstance();

		String name = Util.randomName();
		Faculty faculty = new Faculty(name, department);
		em.persist(faculty);

		// Now schedule their retirement...
		// Average years of service is 30, with an exponential distribution
		// Round to an int so that retirements happen at the end of a year
		double retirement = getTime() + (int) Util.randomExp(30);
		loop.schedule(new RetireFacultyEvent(retirement, faculty));
	}

}

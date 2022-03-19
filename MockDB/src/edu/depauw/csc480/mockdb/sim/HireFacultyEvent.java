package edu.depauw.csc480.mockdb.sim;

import edu.depauw.csc480.mockdb.db.EntityManager;
import edu.depauw.csc480.mockdb.model.Department;
import edu.depauw.csc480.mockdb.model.Faculty;

public class HireFacultyEvent extends AbstractEvent {
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
		System.out.println("Hire " + faculty + " at time " + getTime());
		
		// Now schedule their retirement...
		double retirement = getTime() + (int) Util.randomExp(30);
		loop.schedule(new RetireFacultyEvent(retirement, faculty));
	}

}

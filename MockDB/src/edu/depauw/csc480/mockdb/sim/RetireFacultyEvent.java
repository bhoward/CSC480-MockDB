package edu.depauw.csc480.mockdb.sim;

import edu.depauw.csc480.mockdb.model.Department;
import edu.depauw.csc480.mockdb.model.Faculty;

public class RetireFacultyEvent extends AbstractEvent implements Event {
	private Faculty faculty;

	public RetireFacultyEvent(double time, Faculty faculty) {
		super(time);
		this.faculty = faculty;
	}

	@Override
	public void perform(EventLoop loop) {
		Department department = faculty.getDepartment();
		department.removeFaculty(faculty);

		// Hire replacement
		loop.schedule(new HireFacultyEvent(getTime(), department));
	}

}

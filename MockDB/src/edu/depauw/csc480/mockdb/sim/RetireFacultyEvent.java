package edu.depauw.csc480.mockdb.sim;

import edu.depauw.csc480.mockdb.model.Department;
import edu.depauw.csc480.mockdb.model.Faculty;

/**
 * A simulation Event that occurs when a Faculty member retires. They are
 * removed from active participation in their department and a replacement is
 * hired.
 * 
 * @author bhoward
 */
public class RetireFacultyEvent extends AbstractEvent implements Event {
	private Faculty faculty;

	/**
	 * Construct a RetireFacultyEvent object for the given time and Faculty member.
	 * 
	 * @param time
	 * @param faculty
	 */
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

package edu.depauw.csc480.mockdb.sim;

import edu.depauw.csc480.mockdb.model.Student;

public class RegistrationEvent extends AbstractEvent implements Event {
	private Student student;

	public RegistrationEvent(double time, Student student) {
		super(time);
		this.student = student;
	}

	@Override
	public void perform(EventLoop loop) {
		// TODO Auto-generated method stub
		
		// If able to graduate, set graduation year and remove from department majors;
		// Otherwise do the following

		// Choose the next course in the major, if any
		
		// Choose additional courses not in the major:
		// Pick random non-major departments;
		// look for next course in sequence (if any)
		// until four courses total are registered
		
		// Schedule registration again for next year
	}

}

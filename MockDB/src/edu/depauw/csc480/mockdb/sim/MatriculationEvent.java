package edu.depauw.csc480.mockdb.sim;

import edu.depauw.csc480.mockdb.db.EntityManager;
import edu.depauw.csc480.mockdb.model.Department;
import edu.depauw.csc480.mockdb.model.Student;

public class MatriculationEvent extends AbstractEvent implements Event {
	private Department department;
	private int numStudents;

	public MatriculationEvent(double time, Department department, int numStudents) {
		super(time);
		this.department = department;
		this.numStudents = numStudents;
	}

	@Override
	public void perform(EventLoop loop) {
		EntityManager em = EntityManager.getInstance();

		for (int i = 0; i < numStudents; i++) {
			String name = Util.randomName();
			int graduationYear = Util.computeYear(getTime()) + 4;
			Student student = new Student(name, department, graduationYear);
			em.persist(student);
			
			// Schedule this student's registration/graduation
			// Happens later in the year, after sections are assigned to faculty
			loop.schedule(new RegistrationEvent(getTime() + 0.5, student));
		}
		
		// Schedule more students next year
		loop.schedule(new MatriculationEvent(getTime() + 1, department, numStudents));
	}

}

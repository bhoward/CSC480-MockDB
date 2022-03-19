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
			int year = Util.computeYear(getTime()) + 4;
			em.persist(new Student(name, department, year));
			
			// TODO schedule this student's registration/graduation
			// need to choose classes (next course for major, and three others,
			// taking prerequisites and possible failures into account), and
			// check for ability to graduate -- might need to extend graduation year
		}
		
		// Schedule more students next year
		loop.schedule(new MatriculationEvent(getTime() + 1, department, numStudents));
	}

}

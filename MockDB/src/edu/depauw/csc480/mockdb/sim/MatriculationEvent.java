package edu.depauw.csc480.mockdb.sim;

import edu.depauw.csc480.mockdb.db.EntityManager;
import edu.depauw.csc480.mockdb.model.Department;
import edu.depauw.csc480.mockdb.model.Student;

/**
 * A simulation Event that occurs once per year per Department, in which a fixed
 * number of additional majors are granted admission.
 * 
 * @author bhoward
 */
public class MatriculationEvent extends AbstractEvent implements Event {
	private Department department;
	private int numStudents;

	/**
	 * Construct a MatriculationEvent object for the given time, Department, and
	 * number of incoming students. Each student will then have their annual
	 * RegistrationEvents scheduled (until they finally graduate).
	 * 
	 * @param time
	 * @param department
	 * @param numStudents
	 */
	public MatriculationEvent(double time, Department department, int numStudents) {
		super(time, Config.MATRICULATION_TIME);
		this.department = department;
		this.numStudents = numStudents;
	}

	@Override
	public void perform(EventLoop loop) {
		EntityManager em = EntityManager.getInstance();

		for (int i = 0; i < numStudents; i++) {
			String name = Util.randomName();
			int graduationYear = Util.computeYear(getTime()) + Config.EXPECTED_YEARS_TO_GRADUATION;
			Student student = new Student(name, department, graduationYear);
			em.persist(student);

			// Schedule this student's registration/graduation
			// Happens later in the year, after sections are assigned to faculty
			loop.schedule(new RegistrationEvent(getTime(), student));
		}

		// Schedule more students next year
		loop.schedule(new MatriculationEvent(getTime() + 1, department, numStudents));
	}

}

package edu.depauw.csc480.mockdb.sim;

import edu.depauw.csc480.mockdb.db.EntityManager;
import edu.depauw.csc480.mockdb.model.Department;

/**
 * A simulation Event corresponding to the creation of a Department, along with
 * the creation of a list of courses, the hiring of its initial faculty, and the
 * scheduling of future majors and course sections.
 * 
 * @author bhoward
 */
public class CreateDepartmentEvent extends AbstractEvent implements Event {
	private String name;

	/**
	 * Construct a CreateDepartmentEvent object for the given time and department
	 * name.
	 * 
	 * @param time
	 * @param name
	 */
	public CreateDepartmentEvent(int time, String name) {
		super(time, Config.CREATE_DEPARTMENT_TIME);
		this.name = name;
	}

	@Override
	public void perform(EventLoop loop) {
		EntityManager em = EntityManager.getInstance();

		Department department = new Department(name);
		em.persist(department);

		// Add courses to the catalog
		Config.createCourses(department, em);

		// Hire one faculty member now
		loop.schedule(new HireFacultyEvent(getTime(), department));

		// Schedule incoming majors each year
		loop.schedule(new MatriculationEvent(getTime(), department, Config.STUDENTS_PER_MAJOR));

		// Schedule assigning sections each year -- do it in the middle of the year
		// so the faculty roster will be full
		loop.schedule(new AssignSectionsEvent(getTime(), department));
	}
}

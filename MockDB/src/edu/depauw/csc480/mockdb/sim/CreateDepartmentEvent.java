package edu.depauw.csc480.mockdb.sim;

import edu.depauw.csc480.mockdb.db.EntityManager;
import edu.depauw.csc480.mockdb.model.Department;

public class CreateDepartmentEvent extends AbstractEvent implements Event {
	private String name;

	public CreateDepartmentEvent(int time, String name) {
		super(time);
		this.name = name;
	}

	@Override
	public void perform(EventLoop loop) {
		EntityManager em = EntityManager.getInstance();

		Department department = new Department(name);
		em.persist(department);

		// Add courses to the catalog
		Config.createCourses(department, em);

		// Hire some faculty members now
		for (int i = 0; i < Config.FACULTY_PER_MAJOR; i++) {
			loop.schedule(new HireFacultyEvent(getTime(), department));
		}

		// Schedule incoming majors each year
		loop.schedule(new MatriculationEvent(getTime() + 0.25, department, Config.STUDENTS_PER_MAJOR));

		// Schedule assigning sections each year -- do it in the middle of the year
		// so the faculty roster will be full
		loop.schedule(new AssignSectionsEvent(getTime() + 0.5, department));
	}

}

package edu.depauw.csc480.mockdb.sim;

import edu.depauw.csc480.mockdb.db.EntityManager;
import edu.depauw.csc480.mockdb.model.Course;
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

		// Create courses
		em.persist(new Course(name + " for Non-Majors", department));
		em.persist(new Course("Beginning " + name, department));
		em.persist(new Course("Intermediate " + name, department));
		em.persist(new Course("Advanced " + name, department));
		em.persist(new Course("Special Topics in " + name, department));
		em.persist(new Course("Senior Project in " + name, department));

		// Hire three faculty members now
		loop.schedule(new HireFacultyEvent(getTime(), department));
		loop.schedule(new HireFacultyEvent(getTime(), department));
		loop.schedule(new HireFacultyEvent(getTime(), department));

		// Schedule assigning sections each year -- do it in the middle of the year
		// so the faculty roster will be full
		loop.schedule(new AssignSectionsEvent(getTime() + 0.5, department));

		// Assumptions: each department offers six courses:
		// Beginning X, Intermediate X, Advanced X, Senior Project
		// X for Non-Majors, and Special Topics
		// Each is taught once per year, with unlimited section size (?)
		// Each faculty member teaches two courses per year
		// Each department has three faculty members, who can each teach all courses
		// Each department has a major, which requires five courses (all but
		// "Non-Majors")
		// Each student takes four courses per year, and to graduate must
		// * pass fifteen courses (D or above)
		// * pass all five in their major
		// If they get an F, they may retake that course another year
		// When faculty are hired, they are also scheduled a retirement event
	}

}

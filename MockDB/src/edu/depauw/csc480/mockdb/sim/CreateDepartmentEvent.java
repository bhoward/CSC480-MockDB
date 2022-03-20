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

		createCourses(department, em);

		// Hire three faculty members now
		loop.schedule(new HireFacultyEvent(getTime(), department));
		loop.schedule(new HireFacultyEvent(getTime(), department));
		loop.schedule(new HireFacultyEvent(getTime(), department));

		// Schedule incoming majors each year
		loop.schedule(new MatriculationEvent(getTime() + 0.25, department, Config.STUDENTS_PER_MAJOR));

		// Schedule assigning sections each year -- do it in the middle of the year
		// so the faculty roster will be full
		loop.schedule(new AssignSectionsEvent(getTime() + 0.5, department));

		// Assumptions: each department offers six courses:
		// X for Non-Majors, Beginning X, Intermediate X, Advanced X,
		// Special Topics in X, and Senior Project in X
		// Must take in that order (non-majors don't take senior project, majors start
		// with "Beginning")
		// Special Topics and Senior Project are both taken senior year (typically)
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
		// Each department matriculates a fixed number of students per year
	}

	private void createCourses(Department department, EntityManager em) {
		Course x001 = new Course(name + " for Non-Majors", department, Course.NON_MAJOR);
		em.persist(x001);
		Course x101 = new Course("Beginning " + name, department, Course.ANY, x001);
		em.persist(x101);
		Course x201 = new Course("Intermediate " + name, department, Course.ANY, x101);
		em.persist(x201);
		Course x301 = new Course("Advanced " + name, department, Course.ANY, x201);
		em.persist(x301);
		Course x401 = new Course("Special Topics in " + name, department, Course.MAJOR, x301);
		em.persist(x401);
		Course x499 = new Course("Senior Project in " + name, department, Course.MAJOR, x301);
		em.persist(x499);
	}
}

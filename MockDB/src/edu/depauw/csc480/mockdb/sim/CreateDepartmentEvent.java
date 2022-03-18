package edu.depauw.csc480.mockdb.sim;

import edu.depauw.csc480.mockdb.model.Department;

public class CreateDepartmentEvent extends AbstractEvent {
	private String name;

	public CreateDepartmentEvent(int time, String name) {
		super(time);
		this.name = name;
	}

	@Override
	public void perform(EventLoop loop) {
		Department department = new Department(name);

		// Hire three faculty members now
		loop.schedule(new HireFacultyEvent(getTime(), department));
		loop.schedule(new HireFacultyEvent(getTime(), department));
		loop.schedule(new HireFacultyEvent(getTime(), department));

		// Create courses

		// now schedule hiring faculty, creating courses, and assigning sections each
		// year

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

package edu.depauw.csc480.mockdb.sim;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import edu.depauw.csc480.mockdb.db.EntityManager;
import edu.depauw.csc480.mockdb.model.Course;
import edu.depauw.csc480.mockdb.model.Department;
import edu.depauw.csc480.mockdb.model.Enroll;
import edu.depauw.csc480.mockdb.model.Section;
import edu.depauw.csc480.mockdb.model.Student;

public class RegistrationEvent extends AbstractEvent implements Event {
	private Student student;

	public RegistrationEvent(double time, Student student) {
		super(time);
		this.student = student;
	}

	@Override
	public void perform(EventLoop loop) {
		EntityManager em = EntityManager.getInstance();

		if (student.canGraduate()) {
			student.setGraduationYear(Util.computeYear(getTime()));
			student.getMajor().removeMajor(student);
			System.out.println(student);
			return;
		}

		Collection<Section> schedule = new ArrayList<>();

		// Choose the next course(s) in the major, if any
		Department major = student.getMajor();
		for (Section section : major.getSections()) {
			Course course = section.getCourse();
			if (student.canTake(course)) {
				schedule.add(section);
			}
		}

		// Choose additional courses not in the major:
		// Pick random non-major departments;
		// look for next course in sequence (if any)
		// until four courses total are registered
		List<Department> departments = Department.getDepartments();
		while (schedule.size() < Config.COURSES_PER_YEAR) {
			Department department = Util.selectRandom(departments);
			for (Section section : department.getSections()) {
				if (schedule.contains(section)) {
					continue; // Don't choose the same course twice
				}
				Course course = section.getCourse();
				if (student.canTake(course)) {
					schedule.add(section);
					break; // Only choose one from this department
				}
			}
		}

		// Assign grades and create enrollments
		for (Section section : schedule) {
			String grade = Util.randomGrade(); // TODO get better grades in major courses?
			em.persist(new Enroll(student, section, grade));
		}

		// Schedule registration again for next year
		loop.schedule(new RegistrationEvent(getTime() + 1, student));
	}

}

package edu.depauw.csc480.mockdb.sim;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.depauw.csc480.mockdb.db.EntityManager;
import edu.depauw.csc480.mockdb.model.Course;
import edu.depauw.csc480.mockdb.model.Department;
import edu.depauw.csc480.mockdb.model.Faculty;
import edu.depauw.csc480.mockdb.model.Section;

/**
 * A simulation Event that occurs once per year for each Department, leading to
 * a batch of Sections to be offered for the year. There is one Section per
 * Course hosted by the Department, each taught by one of the Department's
 * Faculty members.
 * 
 * @author bhoward
 */
public class AssignSectionsEvent extends AbstractEvent implements Event {
	private Department department;

	/**
	 * Construct an AssignSectionsEvent for the given Department, to occur at the
	 * given time (simulation year). Sections should be assigned after determining
	 * the Courses and Faculty for the year.
	 * 
	 * @param time
	 * @param department
	 */
	public AssignSectionsEvent(double time, Department department) {
		super(time, Config.ASSIGN_SECTIONS_TIME);
		this.department = department;
	}

	@Override
	public void perform(EventLoop loop) {
		EntityManager em = EntityManager.getInstance();

		List<Course> courses = department.getCourses();

		// Compute prior year enrollments
		Map<Course, Integer> enroll = new HashMap<>();
		for (Course course : courses) {
			enroll.put(course, 0);
		}
		for (Section section : department.getSections()) {
			Course course = section.getCourse();
			enroll.put(course, enroll.get(course) + section.getEnrollment());
		}

		// Factor in prior year enrollment in prerequisite courses
		Map<Course, Integer> demand = new HashMap<>();
		for (Course course : courses) {
			List<Course> prereqs = course.getPrereqs();
			int estimate = 0;

			if (prereqs.isEmpty()) {
				estimate = Config.MAXIMUM_SECTION_SIZE * 2; // TODO tweak this?
			} else {
				boolean allNonMajor = true;
				for (Course prereq : prereqs) {
					if (prereq.mayRegister(department)) {
						allNonMajor = false;
					}

					estimate += enroll.get(prereq);
				}
				estimate = estimate / prereqs.size() + ((allNonMajor) ? Config.STUDENTS_PER_MAJOR : 0);
			}

			int average = (enroll.get(course) + estimate) / 2;
			demand.put(course, average);
		}

		// Now create a list of courses for which we need sections, one copy per section
		List<Course> needed = new ArrayList<>();
		for (Course course : courses) {
			int n = demand.get(course);
			while (n > 0) {
				needed.add(course);
				n -= Config.MAXIMUM_SECTION_SIZE;
			}
		}
		
		department.clearCurrentSections();

		// Compute the year from the simulation time
		int year = Util.computeYear(getTime()) + 1;

		// Assign department faculty to courses randomly
		Collections.shuffle(needed);
		List<Faculty> faculty = new ArrayList<>(department.getFaculty());
		Collections.shuffle(faculty);

		for (int i = 0; i < needed.size(); i++) {
			int j = i % faculty.size();
			Section section = new Section(needed.get(i), faculty.get(j), year);
			department.addSection(section);
			em.persist(section);
		}
		
		// Add faculty to this department next year if enough demand
		if (needed.size() / faculty.size() > Config.PREFERRED_COURSES_PER_FACULTY) {
			loop.schedule(new HireFacultyEvent(getTime() + 1, department));
		}

		// Schedule this again for next year
		loop.schedule(new AssignSectionsEvent(getTime() + 1, department));
	}
}

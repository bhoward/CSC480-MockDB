package edu.depauw.csc480.mockdb.sim;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.depauw.csc480.mockdb.db.EntityManager;
import edu.depauw.csc480.mockdb.model.Course;
import edu.depauw.csc480.mockdb.model.Department;
import edu.depauw.csc480.mockdb.model.Faculty;
import edu.depauw.csc480.mockdb.model.Section;

public class AssignSectionsEvent extends AbstractEvent implements Event {
	private Department department;

	public AssignSectionsEvent(double time, Department department) {
		super(time);
		this.department = department;
	}

	@Override
	public void perform(EventLoop loop) {
		EntityManager em = EntityManager.getInstance();
		
		List<Faculty> faculty = department.getFaculty();
		List<Course> courses = new ArrayList<>(department.getCourses());
		
		department.clearCurrentSections();
		
		// Compute the year from the simulation time
		int year = Util.computeYear(getTime()) + 1;
		
		// Assign department faculty to courses randomly
		Collections.shuffle(courses);
		for (int i = 0; i < courses.size(); i++) {
			int j = i % faculty.size();
			Section section = new Section(courses.get(i), faculty.get(j), year);
			department.addSection(section);
			em.persist(section);
		}

		// Schedule this again for next year
		loop.schedule(new AssignSectionsEvent(getTime() + 1, department));
	}

}

package edu.depauw.csc480.mockdb.model;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import edu.depauw.csc480.mockdb.db.Entity;

/**
 * Model object representing a course taught at a university. Each course has a
 * title and an offering Department, as well as a flag indicating which students
 * may take the course (majors, non-majors, or any) and a list of prerequisites.
 * 
 * @author bhoward
 */
public class Course implements Entity {
	public static final int NON_MAJOR = 0x01;
	public static final int MAJOR = 0x02;
	public static final int ANY = MAJOR | NON_MAJOR;

	private static int nextId = 1;

	private final int id;
	private final String title;
	private final Department department;
	private final int who;
	private final List<Course> prereqs;

	/**
	 * Construct a Course given a title, Department, eligibility flag, and zero or
	 * more prerequisite Courses. A unique id number is automatically assigned.
	 * 
	 * @param title
	 * @param department
	 * @param who
	 * @param prereqs
	 */
	public Course(String title, Department department, int who, Course... prereqs) {
		this.id = nextId++;
		this.title = title;
		this.department = department;
		this.who = who;
		this.prereqs = Arrays.asList(prereqs);

		department.addCourse(this);
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public Department getDepartment() {
		return department;
	}

	public List<Course> getPrereqs() {
		return Collections.unmodifiableList(prereqs);
	}

	/**
	 * Check whether a student with the given major department is allowed to
	 * register for this course. This only considers the eligibility flag; it does
	 * not check prerequisites.
	 * 
	 * @param major The student's major Department
	 * @return true if the student is eligible
	 */
	public boolean mayRegister(Department major) {
		if (major.equals(department)) {
			return (who & MAJOR) != 0;
		} else {
			return (who & NON_MAJOR) != 0;
		}
	}

	@Override
	public String toString() {
		return "Course [id=" + id + ", title=" + title + ", department=" + department.getName() + "]";
	}

	@Override
	public void writeCSV(PrintStream out) {
		// CId, Title, DeptId
		out.println(id + "," + title + "," + department.getId());
	}
}

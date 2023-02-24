package edu.depauw.csc480.mockdb.model;

import java.io.PrintStream;

import edu.depauw.csc480.mockdb.db.Entity;
import edu.depauw.csc480.mockdb.sim.Config;
import edu.depauw.csc480.mockdb.sim.Util;

/**
 * Model object representing a single offering of a course at a university. Each
 * Section keeps track of its Course and the Faculty member teaching it, as well
 * as the year in which it is offered.
 * 
 * @author bhoward
 */
public class Section implements Entity {
	private static int nextId = 1;

	private final int id;
	private final Course course;
	private final Faculty faculty;
	private final int year;
	
	private final int maxEnrollment;
	private int enrollment;

	/**
	 * Construct a Section for the given Course, Faculty, and year. A unique id
	 * number is automatically assigned.
	 * 
	 * @param course
	 * @param faculty
	 * @param year
	 */
	public Section(Course course, Faculty faculty, int year) {
		this.id = nextId++;
		this.course = course;
		this.faculty = faculty;
		this.year = year;

		this.maxEnrollment = (int) (Config.MAXIMUM_SECTION_SIZE + Config.SECTION_SIZE_VARIATION * (Util.randomBoundedGaussian() - 0.5));
		this.enrollment = 0;
	}

	public int getId() {
		return id;
	}

	public Course getCourse() {
		return course;
	}

	public Faculty getFaculty() {
		return faculty;
	}

	public int getYear() {
		return year;
	}

	@Override
	public String toString() {
		return "Section [id=" + id + ", course=" + course.getTitle() + ", faculty=" + faculty.getName() + ", year="
				+ year + ", enrollment=" + enrollment + "]";
	}

	@Override
	public void writeCSV(PrintStream out) {
		// SectId, CourseId, Prof, YearOffered
		out.println(id + "," + course.getId() + "," + faculty.getName() + "," + year);
	}

	/**
	 * Check whether this section has room for another student, and whether the
	 * Student is able to take this Course.
	 * 
	 * @param student
	 * @return
	 */
	public boolean canAdd(Student student) {
		return enrollment < maxEnrollment && student.canTake(course);
	}

	/**
	 * Add the given Student to those taking this section.
	 * 
	 * @param student
	 */
	public void enroll(Student student) {
		enrollment++;
	}

	public Integer getEnrollment() {
		return enrollment;
	}
}

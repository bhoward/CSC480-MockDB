package edu.depauw.csc480.mockdb.model;

import java.io.PrintStream;
import java.util.Collection;
import java.util.HashSet;

import edu.depauw.csc480.mockdb.db.Entity;
import edu.depauw.csc480.mockdb.sim.Config;

/**
 * Model object representing a student at a university. Each student has a name
 * and major Department, as well as an expected (if still enrolled) or actual
 * graduation year. The Student maintains a collection of passed courses, and
 * numbers of credits earned overall and in their major.
 * 
 * @author bhoward
 */
public class Student implements Entity {
	private static int nextId = 1;

	private int id;
	private String name;
	private Department major;
	private int graduationYear;
	private int credits;
	private int majorCredits;
	private Collection<Course> passedCourses;

	/**
	 * Construct a Student with the given name, major Department, and expected
	 * graduation year. A unique id number is automatically assigned.
	 * 
	 * @param name
	 * @param major
	 * @param graduationYear
	 */
	public Student(String name, Department major, int graduationYear) {
		this.id = nextId++;
		this.name = name;
		this.major = major;
		this.graduationYear = graduationYear;

		this.credits = 0;
		this.majorCredits = 0;
		this.passedCourses = new HashSet<>();
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Department getMajor() {
		return major;
	}

	public int getGraduationYear() {
		return graduationYear;
	}

	/**
	 * Update the graduation year when the student actually graduates.
	 * 
	 * @param year
	 */
	public void setGraduationYear(int year) {
		this.graduationYear = year;
	}

	/**
	 * Add an enrollment for this Student, so that they may keep track of credits
	 * and passed courses.
	 * 
	 * @param enroll
	 */
	public void addEnroll(Enroll enroll) {
		if (!enroll.getGrade().equals("F")) {
			Course course = enroll.getSection().getCourse();
			passedCourses.add(course);
			credits++;
			if (course.getDepartment().equals(major)) {
				majorCredits++;
			}
		}
	}

	/**
	 * @return true if the student has enough credits (overall and in the major) to
	 *         graduate
	 */
	public boolean canGraduate() {
		return credits >= Config.GRADUATION_CREDITS && majorCredits >= Config.GRADUATION_MAJOR_CREDITS;
	}

	/**
	 * Check whether the student is allowed to take the given Course. This depends
	 * both on whether they are eligible to register for the course (given their
	 * major) as well as on whether they have taken all of the prerequisite courses
	 * and have not previously passed the given course.
	 * 
	 * @param course
	 * @return true if this Student may take the Course
	 */
	public boolean canTake(Course course) {
		if (!course.mayRegister(major) || passedCourses.contains(course)) {
			return false;
		}

		for (Course prereq : course.getPrereqs()) {
			if (prereq.mayRegister(major) && !passedCourses.contains(prereq)) {
				return false;
			}
		}

		return true;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", major=" + major.getName() + ", graduationYear="
				+ graduationYear + "]";
	}

	@Override
	public void writeCSV(PrintStream out) {
		// SId, SName, GradYear, MajorId
		out.println(id + "," + name + "," + graduationYear + "," + major.getId());
	}
}

package edu.depauw.csc480.mockdb.model;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.depauw.csc480.mockdb.db.Entity;

/**
 * Model object representing a department at a university. Each department has a
 * name, and keeps track of the current faculty assigned to the department,
 * courses taught by the department, and sections of those courses being offered
 * in the current year. The Department class also keeps track of all of the
 * Department objects that have been created.
 * 
 * @author bhoward
 */
public class Department implements Entity {
	private static int nextId = 1;

	private static List<Department> departments = new ArrayList<>();

	/**
	 * @return a list of all Department objects that have been created
	 */
	public static List<Department> getDepartments() {
		return Collections.unmodifiableList(departments);
	}

	private final int id;
	private final String name;

	private final List<Course> currentCourses;
	private final List<Faculty> currentFaculty;
	private final List<Section> currentSections;

	/**
	 * Construct a Department with the given name. A unique id number is
	 * automatically assigned.
	 * 
	 * @param name
	 */
	public Department(String name) {
		this.id = nextId++;
		this.name = name;

		this.currentCourses = new ArrayList<>();
		this.currentFaculty = new ArrayList<>();
		this.currentSections = new ArrayList<>();

		departments.add(this);
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	/**
	 * Add the Faculty member as an active participant in teaching courses for this
	 * Department.
	 * 
	 * @param member
	 */
	public void addFaculty(Faculty member) {
		currentFaculty.add(member);
	}

	/**
	 * Remove the given Faculty member from the Department (for example, upon
	 * retirement).
	 * 
	 * @param member
	 */
	public void removeFaculty(Faculty member) {
		currentFaculty.remove(member);
	}

	/**
	 * Add the Course to those offered by this Department.
	 * 
	 * @param course
	 */
	public void addCourse(Course course) {
		currentCourses.add(course);
	}

	/**
	 * Remove the Course from those offered by this department. (Not currently used)
	 * 
	 * @param course
	 */
	public void removeCourse(Course course) {
		currentCourses.remove(course);
	}

	/**
	 * Add the Section to the offerings for the current year of the simulation (make
	 * it available for registration).
	 * 
	 * @param section
	 */
	public void addSection(Section section) {
		currentSections.add(section);
	}

	/**
	 * Clear out the list of current sections to prepare for a new year.
	 */
	public void clearCurrentSections() {
		currentSections.clear();
	}

	public List<Faculty> getFaculty() {
		return Collections.unmodifiableList(currentFaculty);
	}

	public List<Course> getCourses() {
		return Collections.unmodifiableList(currentCourses);
	}

	public List<Section> getSections() {
		return Collections.unmodifiableList(currentSections);
	}

	@Override
	public String toString() {
		return "Department [id=" + id + ", name=" + name + "]";
	}

	@Override
	public void writeCSV(PrintStream out) {
		// DId, DName
		out.println(id + "," + name);
	}
}

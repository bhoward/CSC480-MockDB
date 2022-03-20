package edu.depauw.csc480.mockdb.model;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.depauw.csc480.mockdb.db.Entity;

public class Department implements Entity {
	private static int nextId = 1;

	private static List<Department> departments = new ArrayList<>();

	public static List<Department> getDepartments() {
		return Collections.unmodifiableList(departments);
	}

	private int id;
	private String name;

	private List<Course> currentCourses;
	private List<Faculty> currentFaculty;
	private List<Student> currentMajors;
	private List<Section> currentSections;
	// TODO evaluate how many of these reverse collections are needed across all the
	// model classes -- for example, what is the best way to get the current sections
	// being offered by a department?

	public Department(String name) {
		this.id = nextId++;
		this.name = name;

		this.currentCourses = new ArrayList<>();
		this.currentFaculty = new ArrayList<>();
		this.currentMajors = new ArrayList<>();
		this.currentSections = new ArrayList<>();

		departments.add(this);
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void addMajor(Student student) {
		currentMajors.add(student);
	}

	public void removeMajor(Student student) {
		currentMajors.remove(student);
	}

	public void addFaculty(Faculty member) {
		currentFaculty.add(member);
	}

	public void removeFaculty(Faculty member) {
		currentFaculty.remove(member);
	}

	public void addCourse(Course course) {
		currentCourses.add(course);
	}

	public void removeCourse(Course course) {
		currentCourses.remove(course);
	}
	
	public void addSection(Section section) {
		currentSections.add(section);
	}
	
	public void clearCurrentSections() {
		currentSections.clear();
	}

	@Override
	public String toString() {
		return "Department [id=" + id + ", name=" + name + "]";
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
	public void writeCSV(PrintStream out) {
		// DId, DName
		out.println(id + "," + name);
	}
}

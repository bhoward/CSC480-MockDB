package edu.depauw.csc480.mockdb.model;

import java.util.ArrayList;
import java.util.Collection;

import edu.depauw.csc480.mockdb.db.Entity;

public class Department implements Entity {
	private static int nextId = 1;

	private int id;
	private String name;

	private Collection<Course> currentCourses;
	private Collection<Faculty> currentFaculty;
	private Collection<Section> currentSections; // TODO do we need this one?
	private Collection<Student> currentMajors;

	public Department(String name) {
		this.id = nextId++;
		this.name = name;

		this.currentCourses = new ArrayList<>();
		this.currentFaculty = new ArrayList<>();
		this.currentSections = new ArrayList<>();
		this.currentMajors = new ArrayList<>();
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

	public void addFaculty(Faculty member) {
		currentFaculty.add(member);
	}

	public void addCourse(Course course) {
		currentCourses.add(course);
	}

	public void addSection(Section section) {
		currentSections.add(section);
	}
}

package edu.depauw.csc480.mockdb.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import edu.depauw.csc480.mockdb.db.Entity;

public class Department implements Entity {
	private static int nextId = 1;

	private int id;
	private String name;

	private Collection<Course> currentCourses;
	private Collection<Faculty> currentFaculty;
	private Collection<Student> currentMajors;

	public Department(String name) {
		this.id = nextId++;
		this.name = name;

		this.currentCourses = new ArrayList<>();
		this.currentFaculty = new ArrayList<>();
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

	@Override
	public String toString() {
		return "Department [id=" + id + ", name=" + name + "]";
	}

	public List<Faculty> getFaculty() {
		return new ArrayList<>(currentFaculty);
	}

	public List<Course> getCourses() {
		return new ArrayList<>(currentCourses);
	}
}

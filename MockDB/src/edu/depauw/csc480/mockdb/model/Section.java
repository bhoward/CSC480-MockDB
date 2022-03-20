package edu.depauw.csc480.mockdb.model;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;

import edu.depauw.csc480.mockdb.db.Entity;

public class Section implements Entity {
	private static int nextId = 1;

	private int id;
	private Course course;
	private Faculty faculty;
	private int year;

	private Collection<Enroll> enrollments;

	public Section(Course course, Faculty faculty, int year) {
		this.id = nextId++;
		this.course = course;
		this.faculty = faculty;
		this.year = year;

		this.enrollments = new ArrayList<>();

		course.addSection(this);
		faculty.addSection(this);
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

	public void addEnroll(Enroll enroll) {
		enrollments.add(enroll);
	}

	@Override
	public String toString() {
		return "Section [id=" + id + ", course=" + course.getTitle() + ", faculty=" + faculty.getName() + ", year=" + year + "]";
	}

	@Override
	public void writeCSV(PrintStream out) {
		// SectId, CourseId, Prof, YearOffered
		out.println(id + "," + course.getId() + "," + faculty.getName() + "," + year);
	}
}

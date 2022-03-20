package edu.depauw.csc480.mockdb.model;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import edu.depauw.csc480.mockdb.db.Entity;

public class Course implements Entity {
	public static final int NON_MAJOR = 0x01;
	public static final int MAJOR = 0x02;
	public static final int ANY = MAJOR | NON_MAJOR;

	private static int nextId = 1;

	private int id;
	private String title;
	private Department department;
	private int who;
	private List<Course> prereqs;

	private Collection<Section> sections;

	public Course(String title, Department department, int who, Course... prereqs) {
		this.id = nextId++;
		this.title = title;
		this.department = department;
		this.who = who;
		this.prereqs = Arrays.asList(prereqs);

		this.sections = new ArrayList<>();

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

	public boolean mayRegister(Department major) {
		if (major.equals(department)) {
			return (who & MAJOR) != 0;
		} else {
			return (who & NON_MAJOR) != 0;
		}
	}
	
	public void addSection(Section section) {
		sections.add(section);
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

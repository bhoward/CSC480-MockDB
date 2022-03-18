package edu.depauw.csc480.mockdb.model;

import java.util.ArrayList;
import java.util.Collection;

public class Course {
	private static int nextId = 1;

	private int id;
	private String title;
	private Department department;

	private Collection<Section> sections;

	public Course(String title, Department department) {
		this.id = nextId++;
		this.title = title;
		this.department = department;

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

	public void addSection(Section section) {
		sections.add(section);
	}
}

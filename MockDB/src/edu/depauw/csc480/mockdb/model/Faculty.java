package edu.depauw.csc480.mockdb.model;

import java.util.ArrayList;
import java.util.Collection;

import edu.depauw.csc480.mockdb.db.Entity;

public class Faculty implements Entity {
	private String name;
	private Department department;

	private Collection<Section> sections;

	public Faculty(String name, Department department) {
		this.name = name;
		this.department = department;

		this.sections = new ArrayList<>();

		department.addFaculty(this);
	}

	public String getName() {
		return name;
	}

	public Department getDepartment() {
		return department;
	}

	public void addSection(Section section) {
		sections.add(section);
	}

	@Override
	public String toString() {
		return "Faculty [name=" + name + ", department=" + department.getName() + "]";
	}
}

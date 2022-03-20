package edu.depauw.csc480.mockdb.model;

import java.io.PrintStream;

import edu.depauw.csc480.mockdb.db.Entity;

/**
 * Model object representing a faculty member at a university. Each faculty
 * member has a name and a home Department.
 * 
 * @author bhoward
 */
public class Faculty implements Entity {
	private String name;
	private Department department;

	/**
	 * Construct a faculty member with the given name and home Department.
	 * 
	 * @param name
	 * @param department
	 */
	public Faculty(String name, Department department) {
		this.name = name;
		this.department = department;

		department.addFaculty(this);
	}

	public String getName() {
		return name;
	}

	public Department getDepartment() {
		return department;
	}

	@Override
	public String toString() {
		return "Faculty [name=" + name + ", department=" + department.getName() + "]";
	}

	@Override
	public void writeCSV(PrintStream out) {
		// Do nothing -- not stored in a table
	}
}

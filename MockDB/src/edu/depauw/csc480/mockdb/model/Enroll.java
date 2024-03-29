package edu.depauw.csc480.mockdb.model;

import java.io.PrintStream;

import edu.depauw.csc480.mockdb.db.Entity;

/**
 * Model object representing one enrollment record at a university. An
 * enrollment tracks an instance of a Student registering for a Section, and
 * also records the grade earned.
 * 
 * @author bhoward
 */
public class Enroll implements Entity {
	private static int nextId = 1;

	private final int id;
	private final Student student;
	private final Section section;
	private final String grade;

	/**
	 * Construct an enrollment record given a Student, Section, and grade ("A"
	 * through "F"). A unique id number is automatically assigned.
	 * 
	 * @param student
	 * @param section
	 * @param grade
	 */
	public Enroll(Student student, Section section, String grade) {
		this.id = nextId++;
		this.student = student;
		this.section = section;
		this.grade = grade;

		student.addEnroll(this);
	}

	public int getId() {
		return id;
	}

	public Student getStudent() {
		return student;
	}

	public Section getSection() {
		return section;
	}

	public String getGrade() {
		return grade;
	}

	@Override
	public String toString() {
		return "Enroll [id=" + id + ", student=" + student.getName() + ", section=" + section + ", grade=" + grade
				+ "]";
	}

	@Override
	public void writeCSV(PrintStream out) {
		// EId, StudentId, SectionId, Grade
		out.println(id + "," + student.getId() + "," + section.getId() + "," + grade);
	}
}

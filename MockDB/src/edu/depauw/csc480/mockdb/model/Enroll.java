package edu.depauw.csc480.mockdb.model;

import java.io.PrintStream;

import edu.depauw.csc480.mockdb.db.Entity;

public class Enroll implements Entity {
	private static int nextId = 1;

	private int id;
	private Student student;
	private Section section;
	private String grade;

	public Enroll(Student student, Section section, String grade) {
		this.id = nextId++;
		this.student = student;
		this.section = section;
		this.grade = grade;

		student.addEnroll(this);
		section.addEnroll(this);
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
		return "Enroll [id=" + id + ", student=" + student.getName() + ", section=" + section + ", grade=" + grade + "]";
	}

	@Override
	public void writeCSV(PrintStream out) {
		// EId, StudentId, SectionId, Grade
		out.println(id + "," + student.getId() + "," + section.getId() + "," + grade);
	}
}

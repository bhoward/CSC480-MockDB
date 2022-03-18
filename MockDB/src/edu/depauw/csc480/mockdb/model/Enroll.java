package edu.depauw.csc480.mockdb.model;

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
}

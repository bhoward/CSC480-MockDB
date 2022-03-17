package edu.depauw.csc480.mockdb.model;

public class Enroll {
	private int id;
	private Student student;
	private Section section;
	private String grade;
	
	public Enroll(int id, Student student, Section section, String grade) {
		this.id = id;
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

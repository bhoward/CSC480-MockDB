package edu.depauw.csc480.mockdb.model;

import java.util.ArrayList;
import java.util.Collection;

public class Student {
	private int id;
	private String name;
	private Department major;
	
	private Collection<Enroll> enrollments;

	public Student(int id, String name, Department major) {
		this.id = id;
		this.name = name;
		this.major = major;
		
		this.enrollments = new ArrayList<>();
		
		major.addMajor(this);
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Department getMajor() {
		return major;
	}

	public void addEnroll(Enroll enroll) {
		enrollments.add(enroll);
	}
}

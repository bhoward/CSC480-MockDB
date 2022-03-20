package edu.depauw.csc480.mockdb.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import edu.depauw.csc480.mockdb.db.Entity;
import edu.depauw.csc480.mockdb.sim.Config;

public class Student implements Entity {
	private static int nextId = 1;

	private int id;
	private String name;
	private Department major;
	private int graduationYear;
	private int credits;
	private int majorCredits;

	private Collection<Enroll> enrollments;
	private Set<Course> passedCourses;

	public Student(String name, Department major, int graduationYear) {
		this.id = nextId++;
		this.name = name;
		this.major = major;
		this.graduationYear = graduationYear;
		
		this.credits = 0;
		this.majorCredits = 0;

		this.enrollments = new ArrayList<>();
		this.passedCourses = new HashSet<>();

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

	public int getGraduationYear() {
		return graduationYear;
	}
	
	public void setGraduationYear(int year) {
		this.graduationYear = year;
	}

	public void addEnroll(Enroll enroll) {
		enrollments.add(enroll);
		if (!enroll.getGrade().equals("F")) {
			Course course = enroll.getSection().getCourse();
			passedCourses.add(course);
			credits++;
			if (course.getDepartment().equals(major)) {
				majorCredits++;
			}
		}
	}
	
	public boolean canGraduate() {
		return credits >= Config.GRADUATION_CREDITS && majorCredits >= Config.GRADUATION_MAJOR_CREDITS;
	}

	public boolean canTake(Course course) {
		if (!course.mayRegister(major) || passedCourses.contains(course)) {
			return false;
		}
		
		for (Course prereq : course.getPrereqs()) {
			if (prereq.mayRegister(major) && !passedCourses.contains(prereq)) {
				return false;
			}
		}
		
		return true;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", major=" + major + ", graduationYear=" + graduationYear + "]";
	}
}

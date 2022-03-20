package edu.depauw.csc480.mockdb.sim;

import java.util.HashMap;
import java.util.Map;

import edu.depauw.csc480.mockdb.db.EntityManager;
import edu.depauw.csc480.mockdb.model.Course;
import edu.depauw.csc480.mockdb.model.Department;

public class Config {
	public static final int SIMULATION_YEARS = 50;
	public static final int STARTING_YEAR = 1970;
	
	public static final int COURSES_PER_YEAR = 4;
	public static final int EXPECTED_YEARS_TO_GRADUATION = 4;
	public static final int GRADUATION_CREDITS = 15;
	public static final int GRADUATION_MAJOR_CREDITS = 5;
	
	public static final int STUDENTS_PER_MAJOR = 20;
	public static final int FACULTY_PER_MAJOR = 3;
	public static final int MEAN_FACULTY_YEARS = 30;

	public static final Map<String, Integer> DEPARTMENT_START;
	static {
		DEPARTMENT_START = new HashMap<>();
		DEPARTMENT_START.put("English", 0);
		DEPARTMENT_START.put("Mathematics", 0);
		DEPARTMENT_START.put("History", 0);
		DEPARTMENT_START.put("Philosophy", 0);
		DEPARTMENT_START.put("Physics", 0);
		DEPARTMENT_START.put("Computer Science", 20);
	}
	
	public static void createCourses(Department department, EntityManager em) {
		String name = department.getName();
		
		Course x001 = new Course(name + " for Non-Majors", department, Course.NON_MAJOR);
		em.persist(x001);
		Course x101 = new Course("Beginning " + name, department, Course.ANY, x001);
		em.persist(x101);
		Course x201 = new Course("Intermediate " + name, department, Course.ANY, x101);
		em.persist(x201);
		Course x301 = new Course("Advanced " + name, department, Course.ANY, x201);
		em.persist(x301);
		Course x401 = new Course("Special Topics in " + name, department, Course.MAJOR, x301);
		em.persist(x401);
		Course x499 = new Course("Senior Project in " + name, department, Course.MAJOR, x301);
		em.persist(x499);
	}
}

package edu.depauw.csc480.mockdb.sim;

import java.util.HashMap;
import java.util.Map;

public class Config {
	public static final int SIMULATION_YEARS = 50;
	public static final int STARTING_YEAR = 1970;
	
	public static final int COURSES_PER_YEAR = 4;
	public static final int EXPECTED_YEARS_TO_GRADUATION = 4;
	public static final int GRADUATION_CREDITS = 15;
	public static final int GRADUATION_MAJOR_CREDITS = 5;
	
	public static final int STUDENTS_PER_MAJOR = 20;

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
}

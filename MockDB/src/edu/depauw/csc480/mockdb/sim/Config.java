package edu.depauw.csc480.mockdb.sim;

import java.util.HashMap;
import java.util.Map;

import edu.depauw.csc480.mockdb.db.EntityManager;
import edu.depauw.csc480.mockdb.model.Course;
import edu.depauw.csc480.mockdb.model.Department;

/**
 * Configuration information for the university simulation.
 * 
 * @author bhoward
 */
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

	/**
	 * An association of Department names with the simulation year in which they are
	 * to be created.
	 */
	public static final Map<String, Integer> DEPARTMENT_START;
	static {
		DEPARTMENT_START = new HashMap<>();
		DEPARTMENT_START.put("English", 0);
		DEPARTMENT_START.put("Mathematics", 0);
		DEPARTMENT_START.put("History", 0);
		DEPARTMENT_START.put("Philosophy", 0);
		DEPARTMENT_START.put("Religion", 0);
		DEPARTMENT_START.put("Music", 0);
		DEPARTMENT_START.put("Art History", 0);
		DEPARTMENT_START.put("Greek", 0);
		DEPARTMENT_START.put("Latin", 0);
		DEPARTMENT_START.put("Chemistry", 0);
		DEPARTMENT_START.put("Biology", 0);
		DEPARTMENT_START.put("Physics", 0);
		DEPARTMENT_START.put("Geology", 5);
		DEPARTMENT_START.put("Geography", 5);
		DEPARTMENT_START.put("Psychology", 5);
		DEPARTMENT_START.put("French", 10);
		DEPARTMENT_START.put("Spanish", 10);
		DEPARTMENT_START.put("German", 10);
		DEPARTMENT_START.put("Italian", 10);
		DEPARTMENT_START.put("Theatre", 15);
		DEPARTMENT_START.put("Art", 15);
		DEPARTMENT_START.put("Economics", 15);
		DEPARTMENT_START.put("Education", 15);
		DEPARTMENT_START.put("Political Science", 15);
		DEPARTMENT_START.put("Computer Science", 20);
		DEPARTMENT_START.put("Japanese", 20);
		DEPARTMENT_START.put("Chinese", 20);
		DEPARTMENT_START.put("Anthropology", 20);
		DEPARTMENT_START.put("Sociology", 20);
		DEPARTMENT_START.put("Communication", 20);
		DEPARTMENT_START.put("Biochemistry", 25);
		DEPARTMENT_START.put("Kinesiology", 25);
		DEPARTMENT_START.put("Neuroscience", 25);
		DEPARTMENT_START.put("Global Health", 25);
		DEPARTMENT_START.put("Film Studies", 25);
	}

	/**
	 * Create a list of courses for the given Department (and persist them in the
	 * EntityManager). Currently creates the same six very generically-named courses
	 * for each department, with the same prerequisite structure.
	 * 
	 * @param department
	 * @param em
	 */
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

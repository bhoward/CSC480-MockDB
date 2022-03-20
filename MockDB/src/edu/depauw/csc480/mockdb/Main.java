package edu.depauw.csc480.mockdb;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import edu.depauw.csc480.mockdb.db.Entity;
import edu.depauw.csc480.mockdb.db.EntityManager;
import edu.depauw.csc480.mockdb.model.Course;
import edu.depauw.csc480.mockdb.model.Department;
import edu.depauw.csc480.mockdb.model.Enroll;
import edu.depauw.csc480.mockdb.model.Section;
import edu.depauw.csc480.mockdb.model.Student;
import edu.depauw.csc480.mockdb.sim.Config;
import edu.depauw.csc480.mockdb.sim.CreateDepartmentEvent;
import edu.depauw.csc480.mockdb.sim.EventLoop;

public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		EventLoop loop = new EventLoop();

		// Create a variety of departments over the years
		// Currently we can only add new departments, not remove them
		for (var p : Config.DEPARTMENT_START.entrySet()) {
			loop.schedule(new CreateDepartmentEvent(p.getValue(), p.getKey()));
		}

		loop.runUntil(Config.SIMULATION_YEARS);

		EntityManager em = EntityManager.getInstance();
//		em.dump(System.out);

		Map<Class<? extends Entity>, PrintStream> outputs = new HashMap<>();
		try (PrintStream course = new PrintStream(new File("course.csv"));
				PrintStream department = new PrintStream(new File("department.csv"));
				PrintStream enroll = new PrintStream(new File("enroll.csv"));
				PrintStream section = new PrintStream(new File("section.csv"));
				PrintStream student = new PrintStream(new File("student.csv"))) {
			outputs.put(Course.class, course);
			outputs.put(Department.class, department);
			outputs.put(Enroll.class, enroll);
			outputs.put(Section.class, section);
			outputs.put(Student.class, student);

			int records = em.writeCSV(outputs);
			System.out.println(records + " records written");
		}
		
		System.out.println("Done");
	}
}

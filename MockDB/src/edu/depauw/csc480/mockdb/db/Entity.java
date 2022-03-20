package edu.depauw.csc480.mockdb.db;

import java.io.PrintStream;

/**
 * An Entity is a model object in the simulation that can be persisted in the
 * EntityManager.
 * 
 * @author bhoward
 */
public interface Entity {
	/**
	 * Write one record of comma-separated values representing this Entity.
	 * 
	 * @param out The PrintStream on which to write the record
	 */
	void writeCSV(PrintStream out);
}

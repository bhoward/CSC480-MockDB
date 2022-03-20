package edu.depauw.csc480.mockdb.db;

import java.io.PrintStream;

public interface Entity {
	void writeCSV(PrintStream out);
}

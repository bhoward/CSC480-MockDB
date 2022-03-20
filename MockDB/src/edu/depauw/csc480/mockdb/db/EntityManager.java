package edu.depauw.csc480.mockdb.db;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * An EntityManager keeps track of all of the Entity objects generated during
 * the course of a simulation, allowing them to be dumped in various formats at
 * the end. It is a singleton, with a single instance created statically when
 * the class is loaded.
 * 
 * @author bhoward
 */
public class EntityManager {
	private Collection<Entity> entities;

	private EntityManager() {
		this.entities = new ArrayList<>();
	}

	private static final EntityManager INSTANCE = new EntityManager();

	/**
	 * @return the single instance of the EntityManager class
	 */
	public static EntityManager getInstance() {
		return INSTANCE;
	}

	/**
	 * Add the given Entity to the collection being managed.
	 * 
	 * @param entity
	 */
	public void persist(Entity entity) {
		entities.add(entity);
	}

	/**
	 * Print all of the entities on the given PrintStream for debugging purposes.
	 * 
	 * @param out
	 */
	public void dump(PrintStream out) {
		for (Entity entity : entities) {
			out.println(entity);
		}
	}

	/**
	 * Print comma-separated value representations of all of the entities. The class
	 * of each Entity is used to determine which (if any) of several PrintStreams
	 * should get the record.
	 * 
	 * @param outputs A map from Class object to the corresponding PrintStream
	 * @return the number of records actually written
	 */
	public int writeCSV(Map<Class<? extends Entity>, PrintStream> outputs) {
		int records = 0;

		for (Entity entity : entities) {
			Class<? extends Entity> clazz = entity.getClass();

			if (outputs.containsKey(clazz)) {
				PrintStream out = outputs.get(clazz);
				entity.writeCSV(out);
				records++;
			}
		}

		return records;
	}
}

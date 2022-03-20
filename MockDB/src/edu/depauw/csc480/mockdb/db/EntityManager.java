package edu.depauw.csc480.mockdb.db;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class EntityManager {
	private Collection<Entity> entities;

	private EntityManager() {
		this.entities = new ArrayList<>();
	}

	public void persist(Entity entity) {
		entities.add(entity);
	}

	private static final EntityManager INSTANCE = new EntityManager();

	public static EntityManager getInstance() {
		return INSTANCE;
	}

	public void dump(PrintStream out) {
		for (Entity entity : entities) {
			out.println(entity);
		}
	}

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

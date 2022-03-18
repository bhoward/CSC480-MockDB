package edu.depauw.csc480.mockdb.db;

import java.util.ArrayList;
import java.util.Collection;

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
}

package com.stevenlr.gameframework.entitysystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.stevenlr.gameframework.entitysystem.components.Component;
import com.stevenlr.gameframework.entitysystem.entities.Entity;

public class EntitySystem {

	public static EntitySystem instance = new EntitySystem();

	private int _nextId = 1;
	private Map<Integer, Entity> _entities;
	private Map<Class, Map<Entity, Component>> _components;

	private EntitySystem() {
		_entities = new HashMap<Integer, Entity>();
		_components = new HashMap<Class, Map<Entity, Component>>();
	}

	private int getNextAvailableId() {
		return _nextId++;
	}

	public void registerEntity(Entity entity) {
		entity.id = getNextAvailableId();
		_entities.put(entity.id, entity);
	}

	public Entity getEntity(int id) {
		Entity entity = _entities.get(id);

		if (entity == null) {
			throw new RuntimeException("Accessing non-existent entity");
		}

		return entity;
	}

	public void removeEntity(Entity entity) {
		entity.removeAllComponents();
		_entities.remove(entity.id);
	}

	public void addComponent(Entity entity, Class<? extends Component> componentType, Component component) {
		Map<Entity, Component> componentStore = _components.get(componentType);

		if (componentStore == null) {
			componentStore = new HashMap<Entity, Component>();
			_components.put(componentType, componentStore);
		}

		componentStore.put(entity, component);
	}

	public <T> T getComponent(Entity entity, Class<T> componentType) {
		Map<Entity, Component> componentStore = _components.get(componentType);
		T component = (T) componentStore.get(entity);

		if (component == null) {
			throw new RuntimeException("Fetching component from entity that does not possess it");
		}

		return component;
	}

	public boolean hasComponent(Entity entity, Class<? extends Component> componentType) {
		Map<Entity, Component> componentStore = _components.get(componentType);
		return componentStore.containsKey(entity);
	}

	public void removeComponent(Entity entity, Class<? extends Component> componentType) {
		Map<Entity, Component> componentStore = _components.get(componentType);
		componentStore.remove(entity);
	}

	public List<Entity> getEntitiesWithComponents(Class<? extends Component>... componentTypes) {
		List<Entity> entities = new ArrayList<Entity>();

		for (Entity entity : _entities.values()) {
			entities.add(entity);
		}

		for (Class<? extends Component> componentType : componentTypes) {
			Map<Entity, Component> componentStore = _components.get(componentType);

			for (int i = 0; i < entities.size(); ++i) {
				if (!componentStore.containsKey(entities.get(i))) {
					entities.remove(entities.get(i));
					--i;
				}
			}
		}

		return entities;
	}
}

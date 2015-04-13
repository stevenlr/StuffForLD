/*
 * Copyright (c) 2015 Steven Le Rouzic
 * See LICENSE.txt for license details
 */

package com.stevenlr.waffle.entitysystem.entities;

import java.util.ArrayList;
import java.util.List;

import com.stevenlr.waffle.entitysystem.EntitySystem;
import com.stevenlr.waffle.entitysystem.components.Component;

public abstract class Entity {

	public int id;

	private List<Class<? extends Component>> _componentTypes;

	public Entity() {
		EntitySystem.instance.registerEntity(this);
		_componentTypes = new ArrayList<Class<? extends Component>>();
	}

	public void addComponent(Class<? extends Component> componentType, Component component) {
		_componentTypes.add(componentType);
		EntitySystem.instance.addComponent(this, componentType, component);
	}

	public <T extends Component> T getComponent(Class<T> componentType) {
		return EntitySystem.instance.getComponent(this, componentType);
	}

	public boolean hasComponent(Class<? extends Component> componentType) {
		return EntitySystem.instance.hasComponent(this, componentType);
	}

	public void removeComponent(Class<? extends Component> componentType) {
		EntitySystem.instance.removeComponent(this, componentType);
	}

	public void removeAllComponents() {
		for (Class<? extends Component> componentType : _componentTypes) {
			EntitySystem.instance.removeComponent(this, componentType);
		}
	}
}

package com.stevenlr.testgame.systems;

import java.util.List;

import com.stevenlr.gameframework.entitysystem.EntitySystem;
import com.stevenlr.gameframework.entitysystem.entities.Entity;
import com.stevenlr.gameframework.entitysystem.systems.System;
import com.stevenlr.testgame.components.MovementComponent;
import com.stevenlr.testgame.components.PositionComponent;

public class MovementSystem extends System {

	private float _gravity;

	public MovementSystem(float gravity) {
		_gravity = gravity;
	}

	public void update(float dt) {
		List<Entity> entities = EntitySystem.instance.getEntitiesWithComponents(MovementComponent.class, PositionComponent.class);

		for (Entity entity : entities) {
			MovementComponent movement = entity.getComponent(MovementComponent.class);
			PositionComponent position = entity.getComponent(PositionComponent.class);

			movement.dy += _gravity * dt;
			position.x += movement.dx * dt;
			position.y += movement.dy * dt;
		}
	}
}

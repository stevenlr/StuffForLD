package com.stevenlr.testgame.systems;

import java.util.List;

import com.stevenlr.gameframework.entitysystem.EntitySystem;
import com.stevenlr.gameframework.entitysystem.entities.Entity;
import com.stevenlr.gameframework.entitysystem.systems.System;
import com.stevenlr.gameframework.graphics.Renderer;
import com.stevenlr.testgame.components.ColorComponent;
import com.stevenlr.testgame.components.PositionComponent;

public class ColorPointRenderSystem extends System {

	private float _size;

	public ColorPointRenderSystem(float size) {
		_size = size;
	}

	public void draw(Renderer r) {
		List<Entity> entities = EntitySystem.instance.getEntitiesWithComponents(ColorComponent.class, PositionComponent.class);

		for (Entity entity : entities) {
			ColorComponent color = entity.getComponent(ColorComponent.class);
			PositionComponent position = entity.getComponent(PositionComponent.class);

			r.save();
			r.translate(position.x, position.y);
			r.translate(-_size / 2, -_size / 2);
			r.fillRect(position.x, position.y, _size, _size, color.color);
			r.restore();
		}
	}
}

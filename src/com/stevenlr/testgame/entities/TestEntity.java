package com.stevenlr.testgame.entities;

import java.util.Random;

import com.stevenlr.gameframework.entitysystem.entities.Entity;
import com.stevenlr.gameframework.graphics.Color;
import com.stevenlr.testgame.components.ColorComponent;
import com.stevenlr.testgame.components.MovementComponent;
import com.stevenlr.testgame.components.PositionComponent;

public class TestEntity extends Entity {

	public TestEntity() {
		Random r = new Random();

		ColorComponent color = new ColorComponent();
		color.color = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));

		PositionComponent position = new PositionComponent();
		position.x = 0;
		position.y = 0;

		MovementComponent movement = new MovementComponent();
		movement.dx = (r.nextFloat() * 2 - 1) * 60;
		movement.dy = (r.nextFloat() * 2 - 1) * 60;

		addComponent(ColorComponent.class, color);
		addComponent(PositionComponent.class, position);
		addComponent(MovementComponent.class, movement);
	}
}

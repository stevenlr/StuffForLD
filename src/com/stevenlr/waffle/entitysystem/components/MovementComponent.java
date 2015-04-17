/*
 * Copyright (c) 2015 Steven Le Rouzic
 * See LICENSE.txt for license details
 */

package com.stevenlr.waffle.entitysystem.components;

public class MovementComponent extends Component {

	public float dx = 0;
	public float dy = 0;

	public MovementComponent() {
	}

	public MovementComponent(float dx, float dy) {
		this.dx = dx;
		this.dy = dy;
	}
}

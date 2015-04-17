/*
 * Copyright (c) 2015 Steven Le Rouzic
 * See LICENSE.txt for license details
 */

package com.stevenlr.waffle.entitysystem.components;

public class PositionComponent extends Component {

	public float x = 0;
	public float y = 0;

	public PositionComponent() {
	}

	public PositionComponent(float x, float y) {
		this.x = x;
		this.y = y;
	}
}

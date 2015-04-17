/*
 * Copyright (c) 2015 Steven Le Rouzic
 * See LICENSE.txt for license details
 */

package com.stevenlr.waffle.entitysystem.components;

import com.stevenlr.waffle.graphics.Color;

public class ColorComponent extends Component {

	public Color color;

	public ColorComponent() {
		color = new Color(0, 0, 0);
	}

	public ColorComponent(Color color) {
		this.color = color.clone();
	}
}

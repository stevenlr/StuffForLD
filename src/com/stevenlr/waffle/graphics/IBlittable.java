/*
 * Copyright (c) 2015 Steven Le Rouzic
 * See LICENSE.txt for license details
 */

package com.stevenlr.waffle.graphics;

import java.awt.Graphics2D;

public interface IBlittable {

	public int getWidth();
	public int getHeight();
	public void blitOn(Graphics2D graphics, int x, int y, int width, int height);
}

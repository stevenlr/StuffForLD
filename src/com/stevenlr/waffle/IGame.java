/*
 * Copyright (c) 2015 Steven Le Rouzic
 * See LICENSE.txt for license details
 */

package com.stevenlr.waffle;

import com.stevenlr.waffle.graphics.Renderer;

public interface IGame {

	public void initGame();
	public void update(float dt);
	public void draw(Renderer renderer);
}

/*
 * Copyright (c) 2015 Steven Le Rouzic
 * See LICENSE.txt for license details
 */

package com.stevenlr.waffle;

import com.stevenlr.waffle.graphics.Renderer;

public interface IWaffleGame {

	void init();
	void update(float dt);
	void draw(Renderer renderer);
}

package com.stevenlr.waffle;

import com.stevenlr.waffle.graphics.Renderer;

public interface IGame {

	public void init();
	public void update(float dt);
	public void draw(Renderer renderer);
}

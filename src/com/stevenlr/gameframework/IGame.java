package com.stevenlr.gameframework;

import com.stevenlr.gameframework.graphics.Renderer;

public interface IGame {

	public void init();
	public void update(float dt);
	public void draw(Renderer renderer);
}

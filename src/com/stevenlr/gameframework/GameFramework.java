package com.stevenlr.gameframework;

import com.stevenlr.gameframework.graphics.Renderer;

public class GameFramework implements Runnable {

	public static GameFramework instance = new GameFramework();
	
	private static Game _game;
	private Renderer _renderer;
	private int _viewportWidth;
	private int _viewportHeight;
	private int _pixelAspect;

	private GameFramework() {
	}

	public void setViewportSize(int width, int height) {
		if (_game != null) {
			throw new RuntimeException("Can't set viewport size after game has started");
		}

		_viewportWidth = width;
		_viewportHeight = height;
	}

	public void setPixelAspect(int pixelAspect) {
		if (_game != null) {
			throw new RuntimeException("Can't set pixel aspect after game has started");
		}

		_pixelAspect = pixelAspect;
	}

	public void startGame(Game game) {
		_game = game;
		game.init();
		new Thread(this).start();
	}

	@Override
	public void run() {
		_game.update(1.0f);
		_game.draw(_renderer);
	}
}

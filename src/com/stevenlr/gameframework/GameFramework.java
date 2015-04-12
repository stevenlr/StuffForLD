package com.stevenlr.gameframework;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.stevenlr.gameframework.graphics.Canvas;

public class GameFramework implements Runnable {

	public static GameFramework instance = new GameFramework();
	
	private static Game _game;
	private Canvas _canvas;
	private java.awt.Canvas _viewport;

	private int _viewportWidth;
	private int _viewportHeight;
	private int _pixelAspect;
	private String _title = "";

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

	public void setTitle(String title) {
		if (_game != null) {
			throw new RuntimeException("Can't set title after game has started");
		}

		_title = title;
	}

	public void startGame(Game game) {
		_game = game;

		_canvas = new Canvas(_viewportWidth / _pixelAspect, _viewportHeight / _pixelAspect);

		JFrame frame = new JFrame(_title);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		_viewport = new java.awt.Canvas();
		_viewport.setPreferredSize(new Dimension(_viewportWidth, _viewportHeight));

		frame.add(_viewport);
		frame.pack();
		frame.setVisible(true);

		_viewport.createBufferStrategy(2);

		game.init();
		new Thread(this).start();
	}

	@Override
	public void run() {
		long previousTime = System.currentTimeMillis();
		long currentTime;
		float dt;

		while (true) {
			currentTime = System.currentTimeMillis();
			dt = (currentTime - previousTime) / 1000.0f;
			previousTime = currentTime;

			_game.update(dt);
			_game.draw(_canvas.getRenderer());

			Graphics graphics = _viewport.getBufferStrategy().getDrawGraphics();
			graphics.drawImage(_canvas.getImage(), 0, 0, _viewportWidth, _viewportHeight, null);
			graphics.dispose();
			_viewport.getBufferStrategy().show();

			try {
				Thread.sleep(16);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

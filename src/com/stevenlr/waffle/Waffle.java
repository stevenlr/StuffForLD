package com.stevenlr.waffle;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.stevenlr.waffle.entitysystem.EntitySystem;
import com.stevenlr.waffle.graphics.Canvas;
import com.stevenlr.waffle.input.InputHandler;
import com.stevenlr.waffle.input.KeyboardInputHandler;
import com.stevenlr.waffle.input.MouseInputHandler;
import com.stevenlr.waffle.sounds.SoundsManager;

public class Waffle implements Runnable {

	public static Waffle instance = new Waffle();
	public static KeyboardInputHandler keyboard = InputHandler.keyboard;
	public static MouseInputHandler mouse = InputHandler.mouse;
	public static EntitySystem entitySystem = EntitySystem.instance;
	public static SoundsManager sounds = SoundsManager.instance;

	private static IGame _game;
	private Canvas _canvas;
	private java.awt.Canvas _viewport;

	private int _viewportWidth = 800;
	private int _viewportHeight = 600;
	private int _pixelAspect = 1;
	private String _title = "";
	private boolean _showFps = false;

	private Waffle() {
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

	public void setShowFps(boolean showFps) {
		if (_game != null) {
			throw new RuntimeException("Can't set show fps option after game has started");
		}

		_showFps = showFps;
	}

	public void startGame(IGame game) {
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

		InputHandler.registerHandlers(_viewport);
		_viewport.requestFocus();

		game.init();
		new Thread(this).start();
	}

	@Override
	public void run() {
		long frameTimeExpectedMS = 1000 / 60;
		float frameTimeExpectedS = frameTimeExpectedMS / 1000.0f;
		long frameTime;
		long previousTime = System.currentTimeMillis();
		long currentTime;
		float dt;
		float time = 0;
		float frames = 0;

		while (true) {
			currentTime = System.currentTimeMillis();
			dt = (currentTime - previousTime) / 1000.0f;
			previousTime = currentTime;

			float updateTime = dt;
			int simulationSteps = 0;

			while (updateTime > 0.0000001 && simulationSteps < 4) {
				float stepDt = Math.min(updateTime, frameTimeExpectedS);

				_game.update(stepDt);
				updateTime -= stepDt;
				simulationSteps++;
				InputHandler.clean();
			}

			_game.draw(_canvas.getRenderer());

			Graphics graphics = _viewport.getBufferStrategy().getDrawGraphics();
			graphics.drawImage(_canvas.getImage(), 0, 0, _viewportWidth, _viewportHeight, null);
			graphics.dispose();
			_viewport.getBufferStrategy().show();

			frames++;
			time += dt;

			if (time >= 1) {
				if (_showFps) {
					System.out.println(frames);
				}

				frames = 0;
				time = 0;
			}

			frameTime = System.currentTimeMillis() - previousTime;

			if (frameTime < frameTimeExpectedMS) {
				try {
					Thread.sleep(frameTimeExpectedMS - frameTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

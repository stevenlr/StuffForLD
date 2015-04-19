/*
 * Copyright (c) 2015 Steven Le Rouzic
 * See LICENSE.txt for license details
 */

package com.stevenlr.waffle;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.stevenlr.waffle.entitysystem.EntitySystem;
import com.stevenlr.waffle.graphics.Canvas;
import com.stevenlr.waffle.input.InputHandler;
import com.stevenlr.waffle.input.KeyboardInputHandler;
import com.stevenlr.waffle.input.MouseInputHandler;

public class Waffle implements Runnable {

	public static Waffle instance = new Waffle();
	public static KeyboardInputHandler keyboard = InputHandler.keyboard;
	public static MouseInputHandler mouse = InputHandler.mouse;
	public static EntitySystem entitySystem = EntitySystem.instance;

	private static IWaffleGame _game;
	private Canvas _canvas;
	private java.awt.Canvas _viewport;

	private int _viewportWidth = 800;
	private int _viewportHeight = 600;
	private int _pixelAspect = 1;
	private String _title = "";
	private boolean _showFps = false;
	private boolean _running = false;
	private Image _icon = null;

	private Waffle() {
	}

	public void setWindowIcon(Image icon) {
		if (_running) {
			throw new RuntimeException("Can't set window icon game has started");
		}

		_icon = icon;
	}

	public void startGame(IWaffleGame game) {
		if (_running) {
			throw new RuntimeException("Can't set game after game has started");
		}

		_game = game;

		WaffleGame annotation = game.getClass().getAnnotation(WaffleGame.class);

		if (annotation != null) {
			_viewportWidth = annotation.viewportWidth();
			_viewportHeight = annotation.viewportHeight();
			_pixelAspect = annotation.pixelAspect();
			_title = annotation.title();
			_showFps = annotation.showFps();
		}

		start();
	}

	@Override
	public void run() {
		float frameTimeExpectedMS = 1000.0f / 60;
		float frameTimeExpectedS = frameTimeExpectedMS / 1000.0f;
		long frameTime;
		long previousTime = System.currentTimeMillis();
		long currentTime;
		float dt;
		float time = 0;
		float frames = 0;

		while (_running) {
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
					Thread.sleep((long) (frameTimeExpectedMS - frameTime));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void start() {
		if (_game == null) {
			throw new RuntimeException("No game has been set");
		}

		_running = true;
		_canvas = new Canvas(_viewportWidth / _pixelAspect, _viewportHeight / _pixelAspect);
		_viewport = new java.awt.Canvas();
		_viewport.setPreferredSize(new Dimension(_viewportWidth, _viewportHeight));

		JFrame frame = new JFrame(_title);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setIgnoreRepaint(true);

		if (_icon != null) {
			frame.setIconImage(_icon);
		}

		frame.add(_viewport);
		frame.pack();
		frame.setVisible(true);

		_viewport.createBufferStrategy(2);

		InputHandler.registerHandlers(_viewport);
		_viewport.requestFocus();

		_game.init();

		new Thread(this).start();
	}

	public void stop() {
		_running = false;
	}
}

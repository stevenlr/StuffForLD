package com.stevenlr.testgame;

import java.awt.event.KeyEvent;

import com.stevenlr.gameframework.IGame;
import com.stevenlr.gameframework.GameFramework;
import com.stevenlr.gameframework.graphics.Color;
import com.stevenlr.gameframework.graphics.Renderer;
import com.stevenlr.gameframework.graphics.Sprite;
import com.stevenlr.gameframework.graphics.SpriteSheet;
import com.stevenlr.gameframework.sounds.Sound;

import com.stevenlr.testgame.entities.TestEntity;
import com.stevenlr.testgame.systems.ColorPointRenderSystem;
import com.stevenlr.testgame.systems.MovementSystem;

public class TestGame implements IGame {

	public static final int WIDTH = 400;
	public static final int HEIGHT = 300;
	public static final int PIXEL_SIZE = 2;

	private float _time = 0;
	private Sprite _image;
	private SpriteSheet.Region[] _regions = new SpriteSheet.Region[4];
	private SpriteSheet.Region _region2;
	private MovementSystem _movementSystem = new MovementSystem(40f);
	private ColorPointRenderSystem _renderSystem = new ColorPointRenderSystem(10);

	public static void main(String[] args) {
		GameFramework.instance.setViewportSize(WIDTH * PIXEL_SIZE, HEIGHT * PIXEL_SIZE);
		GameFramework.instance.setPixelAspect(PIXEL_SIZE);
		GameFramework.instance.setTitle("lolz lolz bloop lolz");
		GameFramework.instance.setShowFps(true);
		GameFramework.instance.startGame(new TestGame());

		GameFramework.sounds.addSound("s1", new Sound("/s1.wav", 0.1f));
		GameFramework.sounds.addSound("s2", new Sound("/s2.wav"));
	}

	@Override
	public void init() {
		SpriteSheet sprites = new SpriteSheet("/spritesheet.png", 16, 16);
		
		_image = new Sprite("/image.png");

		for (int i = 0; i < 4; ++i) {
			_regions[i] = sprites.getRegion(i);
		}

		_region2 = sprites.getRegion(0, 1, 2, 1);

		for (int i = 0; i < 1000; ++i) {
			new TestEntity();
		}
	}

	@Override
	public void update(float dt) {
		_time += dt;

		if (GameFramework.keyboard.isDown(KeyEvent.VK_A)) {
			_movementSystem.update(dt);
		}

		if (GameFramework.keyboard.isPressedThisFrame(KeyEvent.VK_Z)) {
			GameFramework.sounds.play("s1");
		}

		if (GameFramework.keyboard.isPressedThisFrame(KeyEvent.VK_E)) {
			GameFramework.sounds.play("s2");
		}
	}

	@Override
	public void draw(Renderer r) {
		r.fillRect(0, 0, WIDTH, HEIGHT, new Color(0, 0, 0));

		r.save();
		r.translate(100, 100);
		r.scale(10);
		r.rotate(8, 8, _time);

		r.blit(_image);

		r.fillRect(0, 0, 1, 1, new Color(255, 0, 0));
		r.restore();

		r.save();
		r.translate(300, 0);
		r.blit(_region2);
		r.restore();

		r.save();

		r.translate(30, 30);
		r.rotate(18, 18, _time / -3.0f);
		r.blit(_regions[0]);

		r.translate(20, 0);
		r.blit(_regions[1]);

		r.translate(-20, 20);
		r.blit(_regions[2]);

		r.translate(20, 20);
		r.blit(_regions[3]);

		r.restore();

		r.save();
		r.translate(WIDTH / 2, HEIGHT / 2);
		_renderSystem.draw(r);
		r.restore();

		r.getRaster()[1] = 0xffff00ff;
	}
}

/*
 * Copyright (c) 2015 Steven Le Rouzic
 * See LICENSE.txt for license details
 */

package com.stevenlr.waffle.graphics;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class AnimatedSprite implements IBlittable {

	private SpriteSheet _spritesheet;
	private int _currentFrame = 0;
	private float _time = 0;
	private List<Frame> _frames = new ArrayList<Frame>();

	private class Frame {

		public float duration;
		public SpriteSheet.Region region;

		public Frame(float duration, SpriteSheet.Region region) {
			this.duration = duration;
			this.region = region;
		}
	}

	public AnimatedSprite(String filename, int tileWidth, int tileHeight) {
		_spritesheet = new SpriteSheet(filename, tileWidth, tileHeight);
	}

	public void addFrame(float duration, int tile) {
		addFrame(duration, tile % _spritesheet.getNumTilesX(), tile / _spritesheet.getNumTilesX());
	}

	public void addFrame(float duration, int tileX, int tileY) {
		_frames.add(new Frame(duration, _spritesheet.getRegion(tileX, tileY)));
	}

	public void update(float dt) {
		if (dt * _time < 0) {
			_time = -_time;
		}
		_time += dt;

		while (_time > _frames.get(_currentFrame).duration) {
			_time -= _frames.get(_currentFrame).duration;
			_currentFrame = (_currentFrame + 1) % _frames.size();
		}

		while (_time < -_frames.get(_currentFrame).duration) {
			_time += _frames.get(_currentFrame).duration;
			_currentFrame = (_currentFrame + _frames.size() - 1) % _frames.size();
		}
	}

	@Override
	public int getWidth() {
		return _spritesheet.getTileWidth();
	}

	@Override
	public int getHeight() {
		return _spritesheet.getTileHeight();
	}

	@Override
	public void blitOn(Graphics2D graphics, int x, int y, int width, int height) {
		_frames.get(_currentFrame).region.blitOn(graphics, x, y, width, height);
	}
}

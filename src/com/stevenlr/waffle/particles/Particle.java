/*
 * Copyright (c) 2015 Steven Le Rouzic
 * See LICENSE.txt for license details
 */

package com.stevenlr.waffle.particles;

import java.awt.Graphics2D;

import com.stevenlr.waffle.IUpdatable;
import com.stevenlr.waffle.graphics.IBlittable;

public class Particle implements IBlittable, IUpdatable {

	public float x = 0;
	public float y = 0;
	public float dx = 0;
	public float dy = 0;
	public float age = 0;

	private IBlittable _texture;
	private boolean _isUpdatable;

	public Particle(IBlittable texture) {
		_texture = texture;
		_isUpdatable = _texture instanceof IUpdatable;
	}

	@Override
	public int getWidth() {
		return _texture.getWidth();
	}

	@Override
	public int getHeight() {
		return _texture.getHeight();
	}

	@Override
	public void blitOn(Graphics2D graphics, int x, int y, int width, int height) {
		_texture.blitOn(graphics, x, y, width, height);
	}

	@Override
	public void update(float dt) {
		if (_isUpdatable) {
			((IUpdatable) _texture).update(dt);
		}
	}
}

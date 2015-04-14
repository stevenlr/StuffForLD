/*
 * Copyright (c) 2015 Steven Le Rouzic
 * See LICENSE.txt for license details
 */

package com.stevenlr.waffle.graphics;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Deque;
import java.util.LinkedList;

public class Renderer {

	private Graphics2D _graphics;
	private AffineTransform _transform;
	private Deque<AffineTransform> _transformStack = new LinkedList<AffineTransform>();
	private boolean _isDirty = true;
	private int[] _raster;

	public Renderer(Graphics2D graphics, int[] raster) {
		_graphics = graphics;
		_transform = _graphics.getTransform();
		_raster = raster;
	}

	public int[] getRaster() {
		return _raster;
	}

	public void save() {
		AffineTransform oldTransform = (AffineTransform) _transform.clone();
		_transformStack.addFirst(oldTransform);
	}

	public void restore() {
		if (_transformStack.size() == 0) {
			throw new RuntimeException("Restoring from empty transformation stack");
		}

		_transform = _transformStack.removeFirst();
		_isDirty = true;
	}

	public void translate(float x, float y) {
		_transform.translate(x, y);
		_isDirty = true;
	}

	public void rotate(float theta) {
		_transform.rotate(theta);
		_isDirty = true;
	}

	public void rotate(float cx, float cy, float theta) {
		_transform.translate(cx, cy);
		_transform.rotate(theta);
		_transform.translate(-cx, -cy);
		_isDirty = true;
	}

	public void scale(float s) {
		_transform.scale(s, s);
		_isDirty = true;
	}

	public void scale(float sx, float sy) {
		_transform.scale(sx, sy);
		_isDirty = true;
	}

	private void applyTransform() {
		if (_isDirty) {
			_graphics.setTransform(_transform);
			_isDirty = false;
		}
	}

	public void fillRect(float x, float y, float width, float height, Color color) {
		applyTransform();
		_graphics.setColor(color.toAwtColor());
		_graphics.fillRect((int) x, (int) y, (int) width, (int) height);
	}

	public void blit(IBlittable blittable) {
		blit(blittable, 0, 0, blittable.getWidth(), blittable.getHeight());
	}

	public void blit(IBlittable blittable, float scale) {
		blit(blittable, 0, 0, (int) (blittable.getWidth() * scale), (int) (blittable.getHeight() * scale));
	}

	public void blit(IBlittable blittable, int x, int y) {
		blit(blittable, x, y, blittable.getWidth(), blittable.getHeight());
	}

	public void blit(IBlittable blittable, int x, int y, float scale) {
		blit(blittable, x, y, (int) (blittable.getWidth() * scale), (int) (blittable.getHeight() * scale));
	}

	public void blit(IBlittable blittable, int x, int y, int width, int height) {
		applyTransform();
		blittable.blitOn(_graphics, x, y, width, height);
	}

	public void blitCenter(IBlittable blittable) {
		blitCenter(blittable, 0, 0, blittable.getWidth(), blittable.getHeight());
	}

	public void blitCenter(IBlittable blittable, float scale) {
		blitCenter(blittable, 0, 0, (int) (blittable.getWidth() * scale), (int) (blittable.getHeight() * scale));
	}

	public void blitCenter(IBlittable blittable, int x, int y) {
		blitCenter(blittable, x, y, blittable.getWidth(), blittable.getHeight());
	}

	public void blitCenter(IBlittable blittable, int x, int y, float scale) {
		blitCenter(blittable, x, y, (int) (blittable.getWidth() * scale), (int) (blittable.getHeight() * scale));
	}

	public void blitCenter(IBlittable blittable, int x, int y, int width, int height) {
		applyTransform();
		blittable.blitOn(_graphics, x - width / 2, y - height / 2, width, height);
	}
}

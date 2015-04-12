package com.stevenlr.gameframework.graphics;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Deque;
import java.util.LinkedList;

public class Renderer {

	private Graphics2D _graphics;
	private AffineTransform _transform;
	private Deque<AffineTransform> _transformStack = new LinkedList<AffineTransform>();
	private boolean _isDirty = true;

	public Renderer(Graphics2D graphics) {
		_graphics = graphics;
		_transform = _graphics.getTransform();
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
		applyTransform();
		blittable.blitOn(_graphics);
	}
}

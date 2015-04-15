/*
 * Copyright (c) 2015 Steven Le Rouzic
 * See LICENSE.txt for license details
 */

package com.stevenlr.waffle.graphics;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Composite;
import java.awt.CompositeContext;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.util.Deque;
import java.util.LinkedList;

public class Renderer {

	private Graphics2D _graphics;
	private AffineTransform _transform;
	private Deque<AffineTransform> _transformStack = new LinkedList<AffineTransform>();
	private boolean _isDirty = true;
	private int[] _raster;
	private Blitter _blitter = new Blitter();

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

	public void drawLine(float x1, float y1, float x2, float y2, float thickness, Color color) {
		applyTransform();
		_graphics.setColor(color.toAwtColor());
		_graphics.setStroke(new BasicStroke(thickness));
		_graphics.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
	}

	public class Blitter {

		private float _x;
		private float _y;
		private float _width;
		private float _height;
		private IBlittable _blittable;

		private Blitter() {
		}

		private void begin(IBlittable blittable, float x, float y, float width, float height) {
			_blittable = blittable;
			_x = x;
			_y = y;
			_width = width;
			_height = height;
		}

		public Blitter mirrorX() {
			_x = _x + _width;
			_width = -_width;

			return this;
		}

		public Blitter mirrorY() {
			_y = _y + _height;
			_height = -_height;

			return this;
		}

		public Blitter center() {
			_x = _x - Math.abs(_width) / 2;
			_y = _y - Math.abs(_height) / 2;

			return this;
		}

		public void blit() {
			_blittable.blitOn(_graphics, (int) _x, (int) _y, (int) _width, (int) _height);
		}
	}

	public Blitter beginBlit(IBlittable blittable) {
		return beginBlit(blittable, 0, 0, blittable.getWidth(), blittable.getHeight());
	}

	public Blitter beginBlit(IBlittable blittable, float scale) {
		return beginBlit(blittable, 0, 0, blittable.getWidth() * scale, blittable.getHeight() * scale);
	}

	public Blitter beginBlit(IBlittable blittable, float x, float y) {
		return beginBlit(blittable, x, y, blittable.getWidth(), blittable.getHeight());
	}

	public Blitter beginBlit(IBlittable blittable, float x, float y, float scale) {
		return beginBlit(blittable, x, y, blittable.getWidth() * scale, blittable.getHeight() * scale);
	}

	public Blitter beginBlit(IBlittable blittable, float x, float y, float width, float height) {
		applyTransform();
		_blitter.begin(blittable, x, y, width, height);

		return _blitter;
	}

	private static class CustomComposite implements Composite, CompositeContext {

		public static CustomComposite instance = new CustomComposite();

		private boolean _additive;
		private Color _color;
		private float _colorOpacity;

		private CustomComposite() {
			_additive = false;
			_color = new Color(0, 0, 0);
			_colorOpacity = 0;
		}

		public void setProperties(boolean additive, Color color, float colorOpacity) {
			_additive = additive;
			_color = color;
			_colorOpacity = colorOpacity;
		}

		@Override
		public CompositeContext createContext(ColorModel srcColorModel, ColorModel dstColorModel, RenderingHints hints) {
			return instance;
		}

		@Override
		public void dispose() {
		}

		@Override
		public void compose(Raster src, Raster dstIn, WritableRaster dstOut) {
			int w = Math.min(src.getWidth(), dstIn.getWidth());
			int h = Math.min(src.getHeight(), dstIn.getHeight());

			int[] srcData = new int[w * src.getNumBands()];
			int[] dstData = new int[w * src.getNumBands()];

			for (int y = 0; y < h; ++y) {
				src.getPixels(0, y, w, 1, srcData);
				dstIn.getPixels(0, y, w, 1, dstData);

				for (int x = 0; x < w; ++x) {
					int as = srcData[x * 4 + 3];
					int rs = srcData[x * 4];
					int gs = srcData[x * 4 + 1];
					int bs = srcData[x * 4 + 2];

					int ad = dstData[x * 4 + 3];
					int rd = dstData[x * 4];
					int gd = dstData[x * 4 + 1];
					int bd = dstData[x * 4 + 2];

					rs = (int) Math.min((rs * (1 - _colorOpacity) + _color.r * _colorOpacity), 255);
					gs = (int) Math.min((gs * (1 - _colorOpacity) + _color.g * _colorOpacity), 255);
					bs = (int) Math.min((bs * (1 - _colorOpacity) + _color.b * _colorOpacity), 255);

					float opacity = (float) as / 255.0f;

					if (_additive) {
						rd = (int) Math.min(rs * opacity + rd, 255);
						gd = (int) Math.min(gs * opacity + gd, 255);
						bd = (int) Math.min(bs * opacity + bd, 255);
					} else {
						rd = (int) (rs * opacity + rd * (1 - opacity));
						gd = (int) (gs * opacity + gd * (1 - opacity));
						bd = (int) (bs * opacity + bd * (1 - opacity));
					}

					dstData[x * 4] = rd;
					dstData[x * 4 + 1] = gd;
					dstData[x * 4 + 2] = bd;
					dstData[x * 4 + 3] = ad;
				}

				dstOut.setPixels(0, y, w, 1, dstData);
			}
		}
	}

	public void setCustomComposite(boolean additive, Color color, float colorOpacity) {
		CustomComposite.instance.setProperties(additive, color, colorOpacity);
		_graphics.setComposite(CustomComposite.instance);
	}

	public void endCustomComposite() {
		_graphics.setComposite(AlphaComposite.SrcOver);
	}

	public void drawText(String text, Color color, Font font, float x, float y) {
		drawText(text, color, font, x, y, Font.HorizontalAlign.LEFT, Font.VerticalAlign.BOTTOM);
	}

	public void drawText(String text, Color color, Font font, float x, float y, Font.HorizontalAlign horizontalAlign, Font.VerticalAlign verticalAlign) {
		applyTransform();
		_graphics.setColor(color.toAwtColor());
		_graphics.setFont(font.toAwtFont());

		FontMetrics metrics = _graphics.getFontMetrics();
		float width = metrics.stringWidth(text);
		float height = metrics.getHeight();

		switch (horizontalAlign) {
		case RIGHT:
			x -= width;
			break;
		case MIDDLE:
			x -= width / 2;
			break;
		}

		switch (verticalAlign) {
		case TOP:
			y += height;
			break;
		case MIDDLE:
			y += height / 2;
			break;
		}

		_graphics.drawString(text, x, y);
	}

	public Point2D toScreenSpace(Point2D src) {
		float[] dest = new float[2];
		_transform.transform(new float[]{src.x, src.y}, 0, dest, 0, 1);

		return new Point2D(dest[0], dest[1]);
	}

	public Point2D toWorldSpace(Point2D src) {
		double[] dest = new double[2];

		try {
			_transform.inverseTransform(new double[]{src.x, src.y}, 0, dest, 0, 1);
		} catch (NoninvertibleTransformException e) {
			e.printStackTrace();
		}

		return new Point2D((float) dest[0], (float) dest[1]);
	}
}

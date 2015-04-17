/*
 * Copyright (c) 2015 Steven Le Rouzic
 * See LICENSE.txt for license details
 */

package com.stevenlr.waffle.graphics;

import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Canvas implements IBlittable, IBlittableFactory {

	private int _width;
	private int _height;
	private BufferedImage _image;
	private Renderer _renderer;

	public Canvas(int width, int height) {
		_width = width;
		_height = height;
		_image = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice().getDefaultConfiguration()
				.createCompatibleImage(width, height, Transparency.TRANSLUCENT);
		_renderer = new Renderer(_image.createGraphics(), getRaster());
	}

	public int[] getRaster() {
		return ((DataBufferInt) _image.getRaster().getDataBuffer()).getData();
	}

	@Override
	public int getWidth() {
		return _width;
	}

	@Override
	public int getHeight() {
		return _height;
	}

	public Renderer getRenderer() {
		return _renderer;
	}

	@Override
	public void blitOn(Graphics2D graphics, int x, int y, int width, int height) {
		graphics.drawImage(_image, x, y, width, height, null);
	}

	public BufferedImage getImage() {
		return _image;
	}

	@Override
	public IBlittable getBlittable() {
		return this;
	}
}

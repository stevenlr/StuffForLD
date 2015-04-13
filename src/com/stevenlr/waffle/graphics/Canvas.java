package com.stevenlr.waffle.graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Canvas implements IBlittable {

	private int _width;
	private int _height;
	private BufferedImage _image;
	private Renderer _renderer;

	public Canvas(int width, int height) {
		_width = width;
		_height = height;
		_image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		_renderer = new Renderer(_image.createGraphics(), getRaster());
	}

	public int[] getRaster() {
		return ((DataBufferInt) _image.getRaster().getDataBuffer()).getData();
	}

	public int getWidth() {
		return _width;
	}

	public int getHeight() {
		return _height;
	}

	public Renderer getRenderer() {
		return _renderer;
	}

	@Override
	public void blitOn(Graphics2D graphics) {
		graphics.drawImage(_image, 0, 0, null);
	}

	public BufferedImage getImage() {
		return _image;
	}
}

/*
 * Copyright (c) 2015 Steven Le Rouzic
 * See LICENSE.txt for license details
 */

package com.stevenlr.waffle.graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.stevenlr.waffle.Waffle;

public class Sprite implements IBlittable {

	private BufferedImage _image;

	public Sprite(String filename) {
		try {
			_image = ImageIO.read(Waffle.class.getResourceAsStream(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getWidth() {
		return _image.getWidth();
	}

	@Override
	public int getHeight() {
		return _image.getHeight();
	}

	@Override
	public void blitOn(Graphics2D graphics, int x, int y, int width, int height) {
		graphics.drawImage(_image, x, y, width, height, null);
	}
}

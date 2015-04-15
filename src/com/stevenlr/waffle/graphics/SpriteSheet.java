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

public class SpriteSheet {

	private BufferedImage _image;
	private int _tileWidth;
	private int _tileHeight;
	private int _numTilesX;
	private int _numTilesY;

	public SpriteSheet(String filename, int tileWidth, int tileHeight) {
		try {
			_image = ImageIO.read(Waffle.class.getResourceAsStream(filename));
			_tileWidth = tileWidth;
			_tileHeight = tileHeight;
			_numTilesX = _image.getWidth() / _tileWidth;
			_numTilesY = _image.getHeight() / _tileHeight;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public class Region implements IBlittable {

		private SpriteSheet _spritesheet;
		private int _x;
		private int _y;
		private int _width;
		private int _height;

		protected Region(SpriteSheet spritesheet, int tileX, int tileY, int numTilesX, int numTilesY) {
			_spritesheet = spritesheet;
			_x = tileX * spritesheet._tileWidth;
			_y = tileY * spritesheet._tileHeight;
			_width = numTilesX * spritesheet._tileWidth;
			_height = numTilesY * spritesheet._tileHeight;
		}

		@Override
		public void blitOn(Graphics2D graphics, int x, int y, int width, int height) {
			graphics.drawImage(_spritesheet._image,
					x, y,
					x + width, y + height,
					_x, _y,
					_x + _width, _y + _height,
					null
			);
		}

		@Override
		public int getWidth() {
			return _width;
		}

		@Override
		public int getHeight() {
			return _height;
		}
	}

	public int getTileWidth() {
		return _tileWidth;
	}

	public int getTileHeight() {
		return _tileHeight;
	}

	public int getNumTilesX() {
		return _numTilesX;
	}

	public int getNumTilesY() {
		return _numTilesY;
	}

	public Region getRegion(int tileX, int tileY) {
		return getRegion(tileX, tileY, 1, 1);
	}

	public Region getRegion(int tileX, int tileY, int numTilesX, int numTilesY) {
		if (tileX < 0 || tileX >= _numTilesX
				|| tileY < 0 || tileY >= _numTilesY
				|| tileX + numTilesX - 1 < 0 || tileX + numTilesX - 1 >= _numTilesX
				|| tileY + numTilesY - 1 < 0 || tileY + numTilesY - 1 >= _numTilesY) {
			throw new RuntimeException("Out-of-bounds spritesheet region");
		}
		return new Region(this, tileX, tileY, numTilesX, numTilesY);
	}

	public Region getRegion(int tile) {
		return getRegion(tile % _numTilesX, tile / _numTilesX, 1, 1);
	}

	public Region getRegion(int tile, int numTilesX, int numTilesY) {
		return getRegion(tile % _numTilesX, tile / _numTilesX, numTilesX, numTilesY);
	}
}

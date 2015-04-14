/*
 * Copyright (c) 2015 Steven Le Rouzic
 * See LICENSE.txt for license details
 */

package com.stevenlr.waffle.graphics;

public class Color {

	public static final Color Black = new Color(0, 0, 0);
	public static final Color White = new Color(255, 255, 255);

	public static final Color Red = new Color(255, 0, 0);
	public static final Color Green = new Color(0, 255, 0);
	public static final Color Blue = new Color(0, 0, 255);

	public static final Color Yellow = new Color(255, 255, 0);
	public static final Color Magenta = new Color(255, 0, 255);
	public static final Color Cyan = new Color(0, 255, 255);

	public static final Color DarkGray = new Color(64, 64, 64);
	public static final Color Gray = new Color(128, 128, 128);
	public static final Color LightGray = new Color(192, 192, 192);

	public static final Color DarkRed = new Color(128, 0, 0);
	public static final Color DarkGreen = new Color(0, 128, 0);
	public static final Color DarkBlue = new Color(0, 0, 128);

	public static final Color Purple = new Color(128, 0, 128);
	public static final Color DarkTurquoise = new Color(0, 128, 128);
	public static final Color Olive = new Color(128, 128, 0);

	public static final Color Orange = new Color(255, 128, 0);
	public static final Color Pink = new Color(255, 0, 128);
	public static final Color LightPink = new Color(255, 128, 128);

	public static final Color Apple = new Color(128, 255, 0);
	public static final Color Mint = new Color(0, 255, 128);
	public static final Color Spring = new Color(128, 255, 128);

	public static final Color Violet = new Color(128, 0, 255);
	public static final Color Azure = new Color(0, 128, 255);
	public static final Color SkyBlue = new Color(128, 128, 255);

	public int r;
	public int g;
	public int b;

	public Color(int r, int g, int b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}

	public java.awt.Color toAwtColor() {
		return new java.awt.Color(r, g, b);
	}

	public int toAlphaInt() {
		return 0xff000000 | (r << 16) | (g << 8) | b;
	}

	public int toInt() {
		return (r << 16) | (g << 8) | b;
	}
}

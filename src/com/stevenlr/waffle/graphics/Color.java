/*
 * Copyright (c) 2015 Steven Le Rouzic
 * See LICENSE.txt for license details
 */

package com.stevenlr.waffle.graphics;

public class Color {

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
}

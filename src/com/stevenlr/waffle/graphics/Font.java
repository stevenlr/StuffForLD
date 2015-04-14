/*
 * Copyright (c) 2015 Steven Le Rouzic
 * See LICENSE.txt for license details
 */

package com.stevenlr.waffle.graphics;

import java.awt.FontFormatException;
import java.io.IOException;

import com.stevenlr.waffle.Waffle;

public class Font {

	public enum VerticalAlign {
		BOTTOM,
		MIDDLE,
		TOP
	}

	public enum HorizontalAlign {
		LEFT,
		MIDDLE,
		RIGHT
	}

	private java.awt.Font _font;

	public Font(String filename, float size, boolean bold, boolean italic) {
		try {
			int style = 0;

			if (bold) {
				style = style | java.awt.Font.BOLD;
			}

			if (italic) {
				style = style | java.awt.Font.ITALIC;
			}

			_font = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, Waffle.class.getResourceAsStream(filename));
			_font = _font.deriveFont(style, size);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Font(String filename, float size) {
		this(filename, size, false, false);
	}

	public java.awt.Font toAwtFont() {
		return _font;
	}
}

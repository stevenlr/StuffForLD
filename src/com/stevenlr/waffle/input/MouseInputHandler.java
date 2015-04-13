/*
 * Copyright (c) 2015 Steven Le Rouzic
 * See LICENSE.txt for license details
 */

package com.stevenlr.waffle.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.HashMap;
import java.util.Map;

public class MouseInputHandler implements MouseListener, MouseMotionListener, MouseWheelListener {

	private Map<Integer, ButtonState> _buttons = new HashMap<Integer, ButtonState>();
	private boolean _isDragging = false;
	private int _mouseX = 0;
	private int _mouseY = 0;
	private int _motionX = 0;
	private int _motionY = 0;
	private int _startDragX = 0;
	private int _startDragY = 0;
	private float _wheelRotation = 0;

	private class ButtonState {

		private boolean _isDown = false;
		private boolean _clickedThisFrame = false;
		private boolean _pressedThisFrame = false;
		private boolean _releasedThisFrame = false;

		public void clean() {
			_clickedThisFrame = false;
			_pressedThisFrame = false;
			_releasedThisFrame = false;
			_motionX = 0;
			_motionY = 0;
		}

		public void press() {
			_pressedThisFrame = true;
			_isDown = true;
		}

		public void release() {
			_releasedThisFrame = true;
			_isDown = false;
		}

		public void click() {
			_clickedThisFrame = true;
		}

		private boolean isDown() {
			return _isDown;
		}

		private boolean isClickedThisFrame() {
			return _clickedThisFrame;
		}

		private boolean isPressedThisFrame() {
			return _pressedThisFrame;
		}

		private boolean isReleasedThisFrame() {
			return _releasedThisFrame;
		}
	}

	public void clean() {
		for (ButtonState state : _buttons.values()) {
			state.clean();
		}

		_motionX = 0;
		_motionY = 0;
		_wheelRotation = 0;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		ButtonState state = _buttons.get(e.getButton());

		if (state == null) {
			state = new ButtonState();
			_buttons.put(e.getButton(), state);
		}

		state.click();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		ButtonState state = _buttons.get(e.getButton());

		if (state == null) {
			state = new ButtonState();
			_buttons.put(e.getButton(), state);
		}

		state.press();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		ButtonState state = _buttons.get(e.getButton());

		if (state == null) {
			state = new ButtonState();
			_buttons.put(e.getButton(), state);
		}

		state.release();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	private void processMouseMotion(int x, int y) {
		_motionX += x - _mouseX;
		_motionY += y - _mouseY;
		_mouseX = x;
		_mouseY = y;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (_isDragging == false) {
			_startDragX = e.getX();
			_startDragY = e.getY();
		}

		processMouseMotion(e.getX(), e.getY());
		_isDragging = true;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		processMouseMotion(e.getX(), e.getY());
		_isDragging = false;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		_wheelRotation += e.getPreciseWheelRotation();
	}

	public boolean isDown(int button) {
		ButtonState state = _buttons.get(button);

		if (state == null) {
			return false;
		}

		return state.isDown();
	}

	public boolean isPressedThisFrame(int button) {
		ButtonState state = _buttons.get(button);

		if (state == null) {
			return false;
		}

		return state.isPressedThisFrame();
	}

	public boolean isReleasedThisFrame(int button) {
		ButtonState state = _buttons.get(button);

		if (state == null) {
			return false;
		}

		return state.isReleasedThisFrame();
	}

	public boolean isClickedThisFrame(int button) {
		ButtonState state = _buttons.get(button);

		if (state == null) {
			return false;
		}

		return state.isClickedThisFrame();
	}

	public boolean isDragging() {
		return _isDragging;
	}

	public int getMouseX() {
		return _mouseX;
	}

	public int getMouseY() {
		return _mouseY;
	}

	public int getMotionX() {
		return _motionX;
	}

	public int getMotionY() {
		return _motionY;
	}

	public int getStartDragX() {
		return _startDragX;
	}

	public int getStartDragY() {
		return _startDragY;
	}

	public float getWheelRotation() {
		return _wheelRotation;
	}
}

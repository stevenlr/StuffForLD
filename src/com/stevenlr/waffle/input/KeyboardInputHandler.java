package com.stevenlr.waffle.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

public class KeyboardInputHandler implements KeyListener {

	private String _typed = "";
	private Map<Integer, KeyState> _keys = new HashMap<Integer, KeyState>();
	private boolean _inputStarted = false;

	private class KeyState {

		private boolean _pressedThisFrame = false;
		private boolean _releasedThisFrame = false;
		private boolean _isDown = false;

		public void press() {
			_pressedThisFrame = true;
			_isDown = true;
		}

		public void release() {
			_releasedThisFrame = true;
			_isDown = false;
		}

		public void clean() {
			_pressedThisFrame = false;
			_releasedThisFrame = false;
		}

		private boolean isPressedThisFrame() {
			return _pressedThisFrame;
		}

		private boolean isReleasedThisFrame() {
			return _releasedThisFrame;
		}

		private boolean isDown() {
			return _isDown;
		}
	}

	public void clean() {
		_typed = "";

		for (KeyState state : _keys.values()) {
			state.clean();
		}
	}

	public void startTextInput() {
		_inputStarted = true;
	}

	public void stopTextInput() {
		_inputStarted = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (_inputStarted) {
			_typed += e.getKeyChar();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		KeyState state = _keys.get(e.getKeyCode());

		if (state == null) {
			state = new KeyState();
			_keys.put(e.getKeyCode(), state);
		}

		state.press();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		KeyState state = _keys.get(e.getKeyCode());

		if (state == null) {
			state = new KeyState();
			_keys.put(e.getKeyCode(), state);
		}

		state.release();
	}

	public String getTypedString() {
		return _typed;
	}

	public boolean isDown(int key) {
		KeyState state = _keys.get(key);

		if (state == null) {
			return false;
		}

		return state.isDown();
	}

	public boolean isPressedThisFrame(int key) {
		KeyState state = _keys.get(key);

		if (state == null) {
			return false;
		}

		return state.isPressedThisFrame();
	}

	public boolean isReleasedThisFrame(int key) {
		KeyState state = _keys.get(key);

		if (state == null) {
			return false;
		}

		return state.isReleasedThisFrame();
	}
}

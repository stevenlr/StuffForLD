package com.stevenlr.gameframework.input;

import java.awt.Component;

public class InputHandler {
	public static KeyboardInputHandler keyboard = new KeyboardInputHandler();

	public static void registerHandlers(Component component) {
		component.addKeyListener(keyboard);
	}
}

package com.stevenlr.gameframework.input;

import java.awt.Component;

public class InputHandler {

	public static KeyboardInputHandler keyboard = new KeyboardInputHandler();
	public static MouseInputHandler mouse = new MouseInputHandler();

	public static void registerHandlers(Component component) {
		component.addKeyListener(keyboard);
		component.addMouseListener(mouse);
		component.addMouseMotionListener(mouse);
		component.addMouseWheelListener(mouse);
	}

	public static void clean() {
		keyboard.clean();
		mouse.clean();
	}
}

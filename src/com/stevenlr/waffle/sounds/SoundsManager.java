/*
 * Copyright (c) 2015 Steven Le Rouzic
 * See LICENSE.txt for license details
 */

package com.stevenlr.waffle.sounds;

import java.util.HashMap;
import java.util.Map;

public class SoundsManager {

	public static SoundsManager instance = new SoundsManager();

	private Map<String, Sound> _sounds = new HashMap<String, Sound>();

	private SoundsManager() {
	}

	public void addSound(String name, Sound sound) {
		_sounds.put(name, sound);
	}

	public Sound getSound(String name) {
		Sound sound = _sounds.get(name);

		if (sound == null) {
			throw new RuntimeException("Accessing sound that does not exist");
		}

		return sound;
	}

	public void play(String name) {
		play(name, 1.0f);
	}

	public void play(String name, float gain) {
		getSound(name).play(gain);
	}
}

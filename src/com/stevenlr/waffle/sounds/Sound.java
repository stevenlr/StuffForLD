/*
 * Copyright (c) 2015 Steven Le Rouzic
 * See LICENSE.txt for license details
 */

package com.stevenlr.waffle.sounds;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.stevenlr.waffle.Waffle;

public class Sound implements LineListener {

	private AudioFormat _format;
	private byte[] _buffer;
	private float _gain;

	public Sound(String filename, float gain) {
		try {
			BufferedInputStream fileStream = new BufferedInputStream(Waffle.class.getResourceAsStream(filename));
			AudioInputStream stream = AudioSystem.getAudioInputStream(fileStream);

			_format = stream.getFormat();
			_buffer = new byte[stream.available()];
			_gain = gain;
			stream.read(_buffer);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Sound(String filename) {
		this(filename, 1.0f);
	}

	public void play() {
		play(1.0f);
	}

	public void play(float gain) {
		try {
			final Clip clip = AudioSystem.getClip();

			if (clip == null) {
				return;
			}

			AudioInputStream stream = new AudioInputStream(new ByteArrayInputStream(_buffer), _format, _buffer.length);
			clip.open(stream);

			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(20 * (float) Math.log10((double) gain * _gain));

			(new Thread(new Runnable() {
				@Override
				public void run() {
					synchronized (clip) {
						clip.start();
					}
				}
			})).start();

			clip.addLineListener(this);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(LineEvent event) {
		if (event.getType() == LineEvent.Type.STOP) {
			event.getLine().close();
		}
	}
}

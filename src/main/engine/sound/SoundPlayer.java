package main.engine.sound;

import java.io.*;
import java.net.*;
import java.util.*;

import javax.sound.sampled.*;
import javax.sound.sampled.LineEvent.Type;

import main.engine.*;

/**
 * Singleton responsible for playing audio.
 * @author Andreas - s214971
 *
 */
public class SoundPlayer {

	private static SoundPlayer instance;
	public static SoundPlayer getInstance() {
		if (instance == null)
			instance = new SoundPlayer();
		return instance;
	}
	
	private SoundPlayer() {

	}
	
	public SoundInstance playSound(Sound sound, PlaybackSettings settings) {
		
		if (settings == null)
			throw new EngineException("Settings was null");
		
		SoundInstance playingSound = new SoundInstance(sound, settings);
		
		Thread t = new Thread(() -> playingSound.start());
		t.setDaemon(true);
		t.start();
		
		return playingSound;
	}
}

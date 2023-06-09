package main.engine.sound;

import java.io.*;
import java.net.*;
import java.util.*;

import javax.sound.sampled.*;
import javax.sound.sampled.LineEvent.Type;

import main.engine.*;

/**
 * Singleton responsible for playing audio.
 * @author andreas
 *
 */
public class SoundPlayer {

	private static SoundPlayer instance;
	public static SoundPlayer getInstance() {
		if (instance == null)
			instance = new SoundPlayer();
		return instance;
	}
	
	private float masterVolume;
	
	private SoundPlayer() {
		masterVolume = 0.8f;
	}
	
	public void setMasterVolume(float volume) {
		if (volume < 0.0f || volume > 1.0f)
			throw new EngineException("Volume wasn't between 0 and 1");
		masterVolume = volume;
	}
	
	public float getMasterVolume() {
		return masterVolume;
	}
	
	public SoundInstance playSound(Sound sound, PlaybackSettings settings) {
		
		if (settings == null)
			throw new EngineException("Settings was null");
		
		SoundInstance playingSound = new SoundInstance(sound, settings);
		
		new Thread(() -> playingSound.start()).start();
		
		return playingSound;
	}
}

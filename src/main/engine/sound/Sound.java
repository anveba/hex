package main.engine.sound;

import java.net.URL;

import javax.sound.sampled.*;

import main.engine.EngineException;

/**
 * Represents some playable piece of audio. This is a container for the
 * data that makes up the audio.
 * @author Andreas - s214971
 *
 */
public class Sound {
	
	final AudioFormat format;
	final byte[] data;
	
	private Sound(String path) {
		try {
			URL url = new URL(path);
	        AudioInputStream stream = AudioSystem.getAudioInputStream(url);
	        data = stream.readAllBytes();
	        format = stream.getFormat();
		} catch (Exception e) {
			throw new EngineException("Error playing sound: " + e.toString());
		}
	}
	
	public static Sound load(String path) {
		return new Sound(path);
	}
}

package main.engine.sound;

import java.io.*;
import java.net.*;
import java.util.*;

import javax.sound.sampled.*;
import javax.sound.sampled.LineEvent.Type;

import main.engine.*;
import main.engine.ui.RectButton;
import main.engine.ui.Slider;
import main.engine.ui.ToggleSwitch;

/**
 * Singleton responsible for playing audio.
 * @author Andreas - s214971
 * @author Oliver Grønborg Christensen - s204479
 *
 */
public class SoundPlayer {

	private ArrayList<SoundInstance> sfxSounds;
	private ArrayList<SoundInstance> musicSounds;


	private static SoundPlayer instance;
	public static SoundPlayer getInstance() {
		if (instance == null)
			instance = new SoundPlayer();
		return instance;
	}
	
	private SoundPlayer() {
		sfxSounds = new ArrayList<>();
		musicSounds = new ArrayList<>();
	}

	public void setSfxVolume(float volume) {
		sfxSounds.forEach(s -> s.getSettings().setVolume(volume));

		RectButton.getDefaultPlaybackSettings().setVolume(volume);
		Slider.getDefaultPlaybackSettings().setVolume(volume);
		ToggleSwitch.getDefaultPlaybackSettings().setVolume(volume);
	}

	public void setMusicVolume(float volume) {
		musicSounds.forEach(s -> s.getSettings().setVolume(volume));
	}

	public void playMusic(Sound sound, PlaybackSettings settings) {
		musicSounds.removeIf(soundInstance -> !soundInstance.isPlaying());

		SoundInstance soundInstance = playSound(sound, settings);
		musicSounds.add(soundInstance);
	}
	public void playSfx(Sound sound, PlaybackSettings settings) {
		sfxSounds.removeIf(soundInstance -> !soundInstance.isPlaying());

		SoundInstance soundInstance = playSound(sound, settings);
		sfxSounds.add(soundInstance);
	}


	private SoundInstance playSound(Sound sound, PlaybackSettings settings) {

		if (settings == null)
			throw new EngineException("Settings was null");
		
		SoundInstance playingSound = new SoundInstance(sound, settings);
		
		Thread t = new Thread(() -> playingSound.start());
		t.setDaemon(true);
		t.start();
		
		return playingSound;
	}
}

package main.engine.sound;

import main.engine.EngineException;

/**
 * Contains settings for audio playback such as volume.
 * @author Andreas - s214971
 * @author Oliver Grønborg Christensen - s204479
 *
 */
public class PlaybackSettings {

	private static final int LOOP_ENDLESSLY = -1;
	
	private float volume;
	private int repetitions;
	
	public PlaybackSettings(float volume, int repetitions) {
		setVolume(volume);
		setRepetitions(repetitions);
	}

	public void setVolume(float volume) {
		if (volume < 0.0f || volume > 1.0f)
			throw new EngineException("Volume wasn't between 0 and 1");
		this.volume = volume;
	}

	public void setRepetitions(int repetitions) {
		if (repetitions < 1 && repetitions != LOOP_ENDLESSLY)
			throw new EngineException("Invalid repeition count");
		this.repetitions = repetitions;
	}

	public float getVolume() {
		return volume;
	}

	public int getRepetitions() {
		return repetitions;
	}

	public static int getLoopEndlessly() {
		return LOOP_ENDLESSLY;
	}
}

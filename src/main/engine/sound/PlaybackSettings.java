package main.engine.sound;

import main.engine.EngineException;

/**
 * Contains settings for audio playback such as volume. Immutable.
 * @author Andreas - s214971
 *
 */
public class PlaybackSettings {

	public static final int LOOP_ENDLESSLY = -1;
	
	public final float volume;
	public final int repetitions;
	
	public PlaybackSettings(float volume, int repetitions) {
		if (volume < 0.0f || volume > 1.0f)
			throw new EngineException("Volume wasn't between 0 and 1");

		if (repetitions < 1 && repetitions != LOOP_ENDLESSLY)
			throw new EngineException("Invalid repeition count");
		
		this.volume = volume;
		this.repetitions = repetitions;
	}
	
}

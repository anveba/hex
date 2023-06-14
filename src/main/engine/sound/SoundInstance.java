package main.engine.sound;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import main.engine.EngineException;
import main.engine.Utility;

/**
 * Represents an instance of a played/playing sound. It contains
 * a reference of the sound being played and playback settings.
 * @author Andreas - s214971
 *
 */
public class SoundInstance {
	
	private PlaybackSettings settings;
	private boolean isPlaying;
	private final Sound sound;
	private boolean stopped;
	private boolean started;
	
	SoundInstance(Sound sound, PlaybackSettings settings) {
		this.sound = sound;
		this.settings = settings;
		isPlaying = false;
		stopped = false;
		started = false;
	}
	
	public synchronized boolean isPlaying() {
		return isPlaying;
	}
	
	private synchronized void setAsPlaying() {
		isPlaying = true;
	}
	
	private synchronized void setAsNotPlaying() {
		isPlaying = false;
	}
	
	public synchronized PlaybackSettings getSettings() {
		return settings;
	}
	
	public synchronized void setSettings(PlaybackSettings settings) {
		if (settings == null)
			throw new EngineException("Settings was null");
		this.settings = settings;
	}
	
	public synchronized void stop() {
		stopped = true;
	}
	
	public synchronized boolean isStopped() {
		return stopped;
	}
	
	void start() {
		synchronized(this) {
			if (started)
				throw new EngineException("Sound has already started playing");
			started = true;
		}
		try{
			
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, sound.format);
            SourceDataLine sourceLine = (SourceDataLine) AudioSystem.getLine(info);
            sourceLine.open();
            
            setAsPlaying();
            sourceLine.start();

            PlaybackSettings settings = getSettings();
            for (int i = 0; 
            		(i < settings.repetitions 
            				|| settings.repetitions == PlaybackSettings.LOOP_ENDLESSLY) 
            		&& !isStopped(); i++) {
            	
	            final int bufferSize = 2048;
	            int bytesRead = 0;
	            
	            while(bytesRead < sound.data.length && !isStopped()) {
	            	
	            	settings = getSettings();
	            	if (sourceLine.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
	                	
	                	FloatControl gainControl = ((FloatControl) sourceLine.getControl(FloatControl.Type.MASTER_GAIN));
	                	
	                	float gain = Utility.lerp(settings.volume,
	                			gainControl.getMinimum(), gainControl.getMaximum());
	                	
	                	gainControl.setValue(gain);
	                }
	            	
	            	int length = Math.min(bufferSize, sound.data.length - bytesRead);
	            	sourceLine.write(sound.data, bytesRead, length);  
	            	bytesRead += length;
	            }
        	}
        	
            if (isStopped())
            	sourceLine.flush();
            else
            	sourceLine.drain();
            
            sourceLine.stop();
            setAsNotPlaying();
            
            sourceLine.close();
        } catch(LineUnavailableException e){
        	throw new EngineException("Could not play sound: " + e.toString());
        }
	}
}

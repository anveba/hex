package main.hex;

/**
 * 
 * Class that contains the user preferences (options/settings) to
 * be used in the game.
 * @author Andreas - s214971
 *
 */
public class Preferences {
	
	private static Preferences current = new Preferences();
	
	public static Preferences getCurrent() {
		return current;
	}
	
	public static void setCurrent(Preferences pref) {
		current = pref;
	}
	
	private boolean enable3D;
	private float masterVolume, musicVolume, sfxVolume;
	
	private Preferences() {
		enable3D = true;
		masterVolume = 0.5f;
		musicVolume = 0.5f;
		sfxVolume = 0.5f;
	}
	
	public void enable3D() {
		enable3D = true;
	}
	
	public void disable3D() {
		enable3D = false;
	}

	public boolean is3DEnabled() {
		return enable3D;
	}

	public float getMasterVolume() {
		return masterVolume;
	}

	public void setMasterVolume(float masterVolume) {
		this.masterVolume = masterVolume;
	}

	public float getMusicVolume() {
		return musicVolume;
	}

	public void setMusicVolume(float musicVolume) {
		this.musicVolume = musicVolume;
	}

	public float getSfxVolume() {
		return sfxVolume;
	}

	public void setSfxVolume(float sfxVolume) {
		this.sfxVolume = sfxVolume;
	}
}

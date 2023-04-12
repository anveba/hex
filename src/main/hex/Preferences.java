package main.hex;

/**
 * 
 * Singleton.
 *
 */
public class Preferences {
	
	private static Preferences instance;
	public static Preferences getInstance() {
		if (instance == null)
			instance = new Preferences();
		return instance;
	}
	
	private boolean enable3D;
	
	private Preferences() {
		enable3D = false;
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
}

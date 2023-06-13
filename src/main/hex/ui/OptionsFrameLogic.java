package main.hex.ui;

import main.engine.ui.FrameStack;
import main.hex.Preferences;
import main.hex.serialisation.HexFileSystem;

/**
 * Logic for the options frame.
 * This class is mainly a abstraction layer, used to separate the logic from the UI.
 * The logic class itself does not contain any significant logic, and is mainly calling other static methods
 * (from the Preferences class), to perform the actual logic.
 * This class however, acts as a seperation layers, to decouple the Preferences class from the Options frame.
 *
 * @Author Oliver Grønborg Christensen - s204479
 */


public class OptionsFrameLogic {

    public void exitSettingsButtonPressed() {
    	HexFileSystem.getInstance().savePreferences(Preferences.getCurrent());
        FrameStack.getInstance().pop();
    }

    public void enable3DGraphics() {
        Preferences.getCurrent().enable3D();
    }

    public void enable2DGraphics() {
        Preferences.getCurrent().disable3D();
    }

    public boolean is3DEnabled() {
        return Preferences.getCurrent().is3DEnabled();
    }

    public void setSoundVolume(int volume) {
        Preferences.getCurrent().setSfxVolume(volume/100f);
    }

    public void setMusicVolume(int volume) {
        Preferences.getCurrent().setMusicVolume(volume/100f);
    }

    public void setMasterVolume(int volume) {
        Preferences.getCurrent().setMasterVolume(volume/100f);
    }

    public int getSoundVolume() {
        return (int) (Preferences.getCurrent().getSfxVolume() * 100);
    }

    public int getMusicVolume() {
        return (int) (Preferences.getCurrent().getMusicVolume() * 100);
    }

    public int getMasterVolume() {
        return (int) (Preferences.getCurrent().getMasterVolume() * 100);
    }

}

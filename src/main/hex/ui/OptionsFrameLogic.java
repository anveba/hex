package main.hex.ui;

import main.engine.ui.FrameStack;
import main.hex.Preferences;
import main.hex.serialisation.HexFileSystem;

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
        System.out.println("Sound Volume set to: " + volume);
        //TODO: set sound volume
    }

    public void setMusicVolume(int volume) {
        System.out.println("Music Volume set to: " + volume);
        //TODO: set sound volume
    }

}

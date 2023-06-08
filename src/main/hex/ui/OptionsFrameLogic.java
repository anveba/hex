package main.hex.ui;

import main.engine.ui.FrameStack;
import main.hex.Preferences;

public class OptionsFrameLogic {

    public void exitSettingsButtonPressed() {
        FrameStack.getInstance().pop();
    }

    public void enable3DGraphics() {
        Preferences.getInstance().enable3D();
    }

    public void enable2DGraphics() {
        Preferences.getInstance().disable3D();
    }

    public boolean is3DEnabled() {
        return Preferences.getInstance().is3DEnabled();
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

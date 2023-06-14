package main.hex.ui;

import main.engine.graphics.Colour;
import main.engine.sound.SoundPlayer;
import main.engine.ui.FrameStack;
import main.engine.ui.HexBackground;
import main.engine.ui.UIGroup;
import main.hex.Preferences;
import main.hex.resources.TextureLibrary;
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

    private HexBackground hexBackground;

    public void exitSettingsButtonPressed(UIGroup backgroundGroup) {
    	HexFileSystem.getInstance().savePreferences(Preferences.getCurrent());
        if (FrameStack.getInstance().peekSecond() instanceof MainMenuFrame) {
            FrameStack.getInstance().clear();
            HexBackground background = getHexBackground() == null
                    ? new HexBackground(0.0f, 0.0f, 0.05f, -0.025f,
                            TextureLibrary.BACKGROUND_TILE_GREYSCALE.getTexture(), Colour.Background_Grey)
                    : hexBackground;
            if (hexBackground != null)
                backgroundGroup.removeChild(hexBackground);
            FrameStack.getInstance().push(new MainMenuFrame(background));
        } else {
            FrameStack.getInstance().pop();
        }
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
        SoundPlayer.getInstance().setSfxVolume(volume/100f * getMasterVolume()/100f);
    }

    public void setMusicVolume(int volume) {
        Preferences.getCurrent().setMusicVolume(volume/100f);
        SoundPlayer.getInstance().setMusicVolume(volume/100f * getMasterVolume()/100f);
    }

    public void setMasterVolume(int volume) {
        Preferences.getCurrent().setMasterVolume(volume/100f);
        SoundPlayer.getInstance().setMusicVolume(volume/100f * getMusicVolume()/100f);
        SoundPlayer.getInstance().setSfxVolume(volume/100f * getSfxVolume()/100f);
    }

    public int getSfxVolume() {
        return (int) (Preferences.getCurrent().getSfxVolume() * 100);
    }

    public int getMusicVolume() {
        return (int) (Preferences.getCurrent().getMusicVolume() * 100);
    }

    public int getMasterVolume() {
        return (int) (Preferences.getCurrent().getMasterVolume() * 100);
    }

    public HexBackground getHexBackground() {
        return hexBackground;
    }

    public void setHexBackground(HexBackground hexBackground) {
        this.hexBackground = hexBackground;
    }
}

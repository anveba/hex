package main.hex.resources;

import main.engine.io.ResourceManager;
import main.engine.sound.Sound;

public enum SoundLibrary {
    CLICK1("sounds/ClickOne.wav"),
    CLICK2("sounds/ClickTwo.wav"),
    BACKGROUND_MUSIC("sounds/dasoundtrack.wav");

    private String soundPath;
    SoundLibrary(String texturePath) {
        this.soundPath = texturePath;
    }

    public Sound getSound() {
        return ResourceManager.getInstance().loadSound(soundPath);
    }
}

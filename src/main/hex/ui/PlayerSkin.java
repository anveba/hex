package main.hex.ui;

import main.engine.graphics.*;

public class PlayerSkin {

    private Texture playerTexture;
    private Colour playerColour;

    public PlayerSkin(Texture playerTexture, Colour playerColour) {
        this.playerTexture = playerTexture;
        this.playerColour = playerColour;
    }

    public Texture getPlayerTexture() {
        return playerTexture;
    }

    public Colour getPlayerColour() {
        return playerColour;
    }
}

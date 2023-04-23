package main.hex.player;

import main.engine.graphics.*;

public class PlayerSkin {

    private Texture playerTexture;
    private Colour playerColour;

    public PlayerSkin(Texture playerTexture, Colour playerColour) {
        this.playerTexture = playerTexture;
        this.playerColour = playerColour;
    }

    public Texture getTexture() {
        return playerTexture;
    }

    public Colour getTint() {
        return playerColour;
    }
}

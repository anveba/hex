package main.hex.player;

import main.engine.ResourceManager;
import main.engine.graphics.*;
import main.hex.resources.SkinDatabase;

public class PlayerSkin {

    private int playerTextureId;
    private Colour playerColour;

    public PlayerSkin(int playerTextureId, Colour playerColour) {
        this.playerTextureId = playerTextureId;
        this.playerColour = playerColour;
    }

    public Texture getTexture() {
        return SkinDatabase.getInstance().getTextureFromId(playerTextureId);
    }

    public Colour getTint() {
        return playerColour;
    }
}

package main.hex.player;

import main.engine.graphics.Colour;
import main.engine.graphics.Texture;
import main.hex.resources.SkinDatabase;

/**
 * A player skin consists of a texture and a colour, where the colour tints the texture, achieving the desired skin.
 * PlayerSkin-objects contain the information about texture and colour.
 *
 * @author Oliver Siggaard - s204450
 */

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

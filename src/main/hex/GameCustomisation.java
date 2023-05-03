package main.hex;

import main.engine.ResourceManager;
import main.engine.graphics.Colour;
import main.engine.graphics.Texture;
import main.hex.player.PlayerSkin;

public class GameCustomisation {

    private String player1Name, player2Name;
    private PlayerSkin player1Skin, player2Skin;
    private int timeRestriction;
    private boolean swapRule;
    private boolean playersSwapped;

    private PlayerSkin blankSkin;
    
    public GameCustomisation(String player1Name, String player2Name, PlayerSkin player1Skin, PlayerSkin player2Skin, int timeRestriction, boolean swapRule) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.player1Skin = player1Skin;
        this.player2Skin = player2Skin;
        this.timeRestriction = timeRestriction;
        this.swapRule = swapRule;
        playersSwapped = false;
        blankSkin = new PlayerSkin(
        		ResourceManager.getInstance().loadTexture("textures/board/white_tile.png"), 
        		Colour.White);
    }

    public String getPlayer1Name() {
    	if (playersSwapped)
    		return player2Name;
        return player1Name;
    }

    public String getPlayer2Name() {
    	if (playersSwapped)
    		return player1Name;
        return player2Name;
    }

    public PlayerSkin getPlayer1Skin() {
    	if (playersSwapped)
    		return player2Skin;
        return player1Skin;
    }

    public PlayerSkin getPlayer2Skin() {
    	if (playersSwapped)
    		return player1Skin;
        return player2Skin;
    }

    public int getTimeRestriction() {
        return timeRestriction;
    }

    public boolean getSwapRule() {
        return swapRule;
    }
    
    public PlayerSkin getBlankSkin() {
		return blankSkin;
	}
    
    public void setPlayersAsSwapped() {
    	playersSwapped = true;
    }
}

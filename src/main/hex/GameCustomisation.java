package main.hex;

import main.engine.graphics.Colour;
import main.hex.player.PlayerSkin;
import main.hex.resources.SkinDatabase;

public class GameCustomisation {

    private final String player1Name, player2Name;
    private final PlayerSkin player1Skin, player2Skin;
    private final int initialTimeLimit;
    private final boolean swapRule;
    private boolean playersSwapped;

    private PlayerSkin blankSkin;
    
    public GameCustomisation(String player1Name, String player2Name, 
    		PlayerSkin player1Skin, PlayerSkin player2Skin, 
    		int initialTimeLimit, boolean swapRule) {
    	
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.player1Skin = player1Skin;
        this.player2Skin = player2Skin;
        this.initialTimeLimit = initialTimeLimit;
        this.swapRule = swapRule;
        playersSwapped = false;
        blankSkin = new PlayerSkin(SkinDatabase.defaultTextureId, Colour.White);
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

    public int getInitialTimeLimit() {
        return initialTimeLimit;
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
    
    public String getOriginalPlayer1Name() {
        return player1Name;
    }

    public String getOriginalPlayer2Name() {
        return player2Name;
    }

    public PlayerSkin getOriginalPlayer1Skin() {
        return player1Skin;
    }

    public PlayerSkin getOriginalPlayer2Skin() {
        return player2Skin;
    }
}

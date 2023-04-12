package main.hex.ui;

import main.engine.graphics.Colour;
import main.engine.graphics.Texture;

public class GameCustomisation {

    private String player1Name, player2Name;
    private PlayerSkin player1Skin, player2Skin;
    private int timeRestriction;
    private boolean swapRule;

    public GameCustomisation(String player1Name, String player2Name, PlayerSkin player1Skin, PlayerSkin player2Skin, int timeRestriction, boolean swapRule) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.player1Skin = player1Skin;
        this.player2Skin = player2Skin;
        this.timeRestriction = timeRestriction;
        this.swapRule = swapRule;
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public PlayerSkin getPlayer1Skin() {
        return player1Skin;
    }

    public PlayerSkin getPlayer2Skin() {
        return player2Skin;
    }

    public int getTimeRestriction() {
        return timeRestriction;
    }

    public boolean getSwapRule() {
        return swapRule;
    }
}

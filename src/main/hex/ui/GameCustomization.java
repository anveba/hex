package main.hex.ui;

import main.engine.graphics.Texture;

public class GameCustomization {

    private String player1Name, player2Name;
    private Texture player1Skin, player2Skin;
    private int timeRestriction;
    private boolean swapRule;

    public GameCustomization(String player1Name, String player2Name, Texture player1Skin, Texture player2Skin,
                             int timeRestriction, boolean swapRule) {
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

    public Texture getPlayer1Skin() {
        return player1Skin;
    }

    public Texture getPlayer2Skin() {
        return player2Skin;
    }

    public int getTimeRestriction() {
        return timeRestriction;
    }

    public boolean getSwapRule() {
        return swapRule;
    }
}

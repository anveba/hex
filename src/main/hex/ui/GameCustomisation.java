package main.hex.ui;

import main.engine.graphics.Colour;
import main.engine.graphics.Texture;

public class GameCustomisation {

    private String player1Name, player2Name;
    private Colour player1Colour, player2Colour;
    private Texture player1Texture, player2Texture;
    private int timeRestriction;
    private boolean swapRule;

    public GameCustomisation(String player1Name, String player2Name, Colour player1Colour, Colour player2Colour,
                             Texture player1Texture, Texture player2Texture, int timeRestriction, boolean swapRule) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.player1Colour = player1Colour;
        this.player2Colour = player2Colour;
        this.player1Texture = player1Texture;
        this.player2Texture = player2Texture;
        this.timeRestriction = timeRestriction;
        this.swapRule = swapRule;
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public Colour getPlayer1Colour() {
        return player1Colour;
    }

    public Colour getPlayer2Colour() {
        return player2Colour;
    }

    public Texture getPlayer1Texture() {
        return player1Texture;
    }

    public Texture getPlayer2Texture() {
        return player2Texture;
    }

    public int getTimeRestriction() {
        return timeRestriction;
    }

    public boolean getSwapRule() {
        return swapRule;
    }
}

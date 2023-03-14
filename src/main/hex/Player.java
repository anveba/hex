package main.hex;

public class Player {

    private Tile.Colour playerColour;

    public Player (Tile.Colour playerColour) {
        this.playerColour = playerColour;
    }

    public Tile.Colour getPlayerColour() {
        return playerColour;
    }

    public void setPlayerColour(Tile.Colour newColor) {this.playerColour = newColor;}
}

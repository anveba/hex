package main.hex;

public class Player {

    private Tile.Colour playerColour;
    private boolean winsByVerticalConnection;

    public Player (Tile.Colour playerColour, boolean hasWonByVerticalConnection) {
        this.playerColour = playerColour;
        this.winsByVerticalConnection = hasWonByVerticalConnection;
    }

    public Tile.Colour getPlayerColour() {
        return playerColour;
    }

    public void setPlayerColour(Tile.Colour newColor) {
    	this.playerColour = newColor;
	}
    
    public boolean winsByVerticalConnection() {
    	return winsByVerticalConnection;
    }
}

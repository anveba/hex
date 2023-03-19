package main.hex;

public class Player {

    private Tile.Colour playerColour;
    private boolean hasWonByVerticalConnection;

    public Player (Tile.Colour playerColour, boolean hasWonByVerticalConnection) {
        this.playerColour = playerColour;
        this.hasWonByVerticalConnection = hasWonByVerticalConnection;
    }

    public Tile.Colour getPlayerColour() {
        return playerColour;
    }

    public void setPlayerColour(Tile.Colour newColor) {
    	this.playerColour = newColor;
	}
    
    public boolean hasWonByVerticalConnection() {
    	return hasWonByVerticalConnection;
    }
}

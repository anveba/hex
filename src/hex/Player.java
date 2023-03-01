package hex;

public class Player {

    private Tile.Colour playerColour;

    public Player (Tile.Colour playerColour) {
        this.playerColour = playerColour;
    }

    public Tile.Colour getPlayerColor() {
        return playerColour;
    }
}

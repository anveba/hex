package main.hex;

public class Tile {

    public enum Colour {
        BLUE,
        RED,
        NONE,
    }

    private Colour colour;

    public Tile(){

    }

    public Colour getColour() {
        return colour;
    }

    public void setColour(Colour c){
        colour = c;
    }
}

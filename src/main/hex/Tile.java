package main.hex;

public class Tile {

    public enum Colour {
        BLUE,
        RED,
        WHITE,
    }

    private Colour colour;

    public static Colour opposite(Colour c){
        if(c == Colour.BLUE){
            return Colour.RED;
        }
        if(c == Colour.RED){
            return Colour.BLUE;
        }
        return Colour.WHITE;
    }
    public Tile(Colour colour){
        if (colour == null) {
            this.colour = Colour.WHITE;
        } else {
            this.colour = colour;
        }

    }

    public Colour getColour() {
        return colour;
    }

    public void setColour(Colour c){
        colour = c;
    }
}

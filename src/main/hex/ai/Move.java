package main.hex.ai;

public class Move {

    private int x;
    private int y;

    private double value;


    public Move(int x, int y) {
        this.x = x;
        this.y = y;
    }



    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double v){
        this.value = v;
    }
}

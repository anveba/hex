package main.hex.ai;

import main.hex.Move;

public class AIMove extends Move {

    private double value;

    public AIMove(int x, int y, double value) {
        super(x, y);
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    void setValue(double v){
        this.value = v;
    }
}

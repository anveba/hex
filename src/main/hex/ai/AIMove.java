package main.hex.ai;

import main.hex.Move;

class AIMove extends Move {

    private double value;

    AIMove(int x, int y, double value) {
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

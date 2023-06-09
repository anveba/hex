package main.hex.ai;

import main.hex.Move;

public class AIMove extends Move{


    private double value;
    private int depth;

    public AIMove(int x, int y, double value,int depth) {
        super(x, y);
        this.value = value;
        this.depth = depth;
    }

    public double getValue() {
        return value;
    }
    public int getDepth(){return depth;}

    void setValue(double v){
        this.value = v;
    }


    public void setDepth(int depth) {
        this.depth = depth;
    }
}

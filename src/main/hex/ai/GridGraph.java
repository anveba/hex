package main.hex.ai;

import main.hex.Tile;

import java.util.Optional;

public class GridGraph extends Graph{

    private final int gridSize;


    public GridGraph(int gridSize) {
        super(gridSize * gridSize + 2);
        this.gridSize = gridSize;

    }

    public int xyToNum(int x, int y){
        return y * gridSize + x;
    }


    public Optional<Double> fadeOfAdjacencyXY(int fromX,int fromY, int toX, int toY) {
        return super.fadeOfAdjacency(xyToNum(fromX,fromY), xyToNum(toX,toY));
    }

    public void connectXyWithFade(int x1, int y1, int x2, int y2, double fade){
        connectWithFade(xyToNum(x1,y1), xyToNum(x2,y2),fade);
    }


}

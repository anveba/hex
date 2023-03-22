package main.hex.ai;

import java.util.Optional;

public class GridGraph extends Graph{

    private final int gridSize;

    public GridGraph(int gridSize) {
        super((gridSize * gridSize) + 2);
        this.gridSize = gridSize;
    }

    public int xyToNum(int x, int y){
        return y * gridSize + x;
    }


    public Optional<Double> fadeOfAdjacencyXY(int fromX,int fromY, int toX, int toY) {
        return super.fadeOfAdjacency(xyToNum(fromX,fromY), xyToNum(toX,toY));
    }

    public void connectStartAndEndNodesVertical(){
        for(int x = 0; x < gridSize; x++){
            connectXyWithFade(x,0,gridSize, gridSize -1,1);
            connectXyWithFade(x, gridSize -1, gridSize +1, gridSize -1,1);
        }
    }

    public void connectStartAndEndNodesHorizontal(){
        for(int y = 0; y < gridSize; y++){
            connectXyWithFade(0,y, gridSize, gridSize -1,1);
            connectXyWithFade(gridSize -1,y, gridSize +1, gridSize -1,1);
        }
    }


    public void connectXyWithFade(int x1, int y1, int x2, int y2, double fade){
        connectWithFade(xyToNum(x1,y1), xyToNum(x2,y2),fade);
    }

    public boolean startAndEndAreConnected(){
        return isConnectedWithMaxFade(getNumberOfNodes()-1,getNumberOfNodes()-2);
    }



}

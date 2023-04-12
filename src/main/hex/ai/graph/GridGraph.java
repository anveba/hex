package main.hex.ai.graph;

import java.util.Optional;

public class GridGraph extends Graph{

    private final int gridSize;


    /*
    Author Nikolaj

    An extension of Graph, that accepts xy-coordinates

     */

    public GridGraph(int gridSize) {
        super((gridSize * gridSize) + 2);
        this.gridSize = gridSize;
    }

    public int xyToNum(int x, int y){
        return y * gridSize + x;
    }


    public Optional<Double> fadeOfAdjacencyXY(int fromX,int fromY, int toX, int toY) {
        return super.weightOfAdjacency(xyToNum(fromX,fromY), xyToNum(toX,toY));
    }

    public void connectStartAndEndNodesVertical(){
        for(int x = 0; x < gridSize; x++){
            connectXyWithWeight(x,0,gridSize, gridSize -1,1);
            connectXyWithWeight(x, gridSize -1, gridSize +1, gridSize -1,1);
        }
    }

    public void connectStartAndEndNodesHorizontal(){
        for(int y = 0; y < gridSize; y++){
            connectXyWithWeight(0,y, gridSize, gridSize -1,1);
            connectXyWithWeight(gridSize -1,y, gridSize +1, gridSize -1,1);
        }
    }


    public void connectXyWithWeight(int x1, int y1, int x2, int y2, double fade){
        connectWithWeight(xyToNum(x1,y1), xyToNum(x2,y2),fade);
    }

    public boolean startAndEndAreConnected(){
        return isConnectedWithWeight1(getNumberOfNodes()-1,getNumberOfNodes()-2);
    }



}

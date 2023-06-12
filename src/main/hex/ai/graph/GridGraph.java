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


    public Optional<Double> weightOfAdjacencyXY(int fromX, int fromY, int toX, int toY) {
        return super.weightOfAdjacency(xyToNum(fromX,fromY), xyToNum(toX,toY));
    }




    public void connectXyWithWeight(int x1, int y1, int x2, int y2, double fade){
        connectWithWeight(xyToNum(x1,y1), xyToNum(x2,y2),fade);
    }

    public boolean startAndEndAreConnected(){
        //printAdjacencies();
        return isConnected(getNumberOfNodes()-1,getNumberOfNodes()-2);
    }





}

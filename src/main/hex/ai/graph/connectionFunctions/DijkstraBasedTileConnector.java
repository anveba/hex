package main.hex.ai.graph.connectionFunctions;

import main.hex.ai.Bridge;
import main.hex.ai.graph.GridGraph;
import main.hex.board.Board;
import main.hex.board.TileColour;

/*
Author Niko
A connection function made to connect to prepare for Dijkstra's shortest path evaluation

Connects tiles based on their colours, with the following rule
(Symmetric, connect(x,y) = connect(y,x))

Agent - Agent -> 0-weight connection
Agent - White -> Low weight connection
White - White -> Medium weight connection
nonAgent - Any -> No connection
 */

public class DijkstraBasedTileConnector extends TileConnectionFunction{

    //Some constant to increase weight with when a tile is uncoloured
    private final float weightConstant = 1;

    @Override
    public void connectTiles(GridGraph gridGraph, Board board, int fromX, int fromY, int toX, int toY, TileColour agentColour) {
        TileColour nonAgentColour = TileColour.opposite(agentColour);

        TileColour t1Colour = board.getTileAtPosition(fromX,fromY).getColour();
        TileColour t2Colour = board.getTileAtPosition(toX,toY).getColour();

        if(t1Colour.equals(nonAgentColour) || t2Colour.equals(nonAgentColour)){
            //System.out.println("Returned");
            return;
        }
        float weight = 0;
        if(t1Colour.equals(TileColour.WHITE)){
            weight += weightConstant;
        }
        if(t2Colour.equals(TileColour.WHITE)){
            weight += weightConstant;
        }
        gridGraph.connectXyWithWeight(fromX,fromY,toX,toY,weight);
    }

    @Override
    public void connectBridge(GridGraph gridGraph, Bridge b) {
        gridGraph.connectXyWithWeight(b.getX1(),b.getY1(), b.getX2(),b.getY2(),0.1);
    }

    @Override
    public double getStartEndWeight() {
        return 0;
    }
}

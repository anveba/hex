package main.hex.ai.graph.connectionFunctions;

import main.hex.ai.graph.GridGraph;
import main.hex.board.Board;
import main.hex.board.TileColour;

public class DijkstraBasedTileConnector extends TileConnectionFunction{

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
    public double getStartEndWeight() {
        return 0;
    }
}

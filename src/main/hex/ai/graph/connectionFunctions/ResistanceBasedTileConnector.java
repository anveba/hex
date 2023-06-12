package main.hex.ai.graph.connectionFunctions;

import main.hex.ai.Bridge;
import main.hex.ai.graph.GridGraph;
import main.hex.board.Board;
import main.hex.board.Tile;
import main.hex.board.TileColour;

public class ResistanceBasedTileConnector extends TileConnectionFunction {
    private double nearZeroWeightConstant = 0.000000001;
    private double weightConstant = 1;

    @Override
    public void connectTiles(GridGraph gridGraph, Board board, int fromX, int fromY, int toX, int toY, TileColour agentColour) {
        TileColour nonAgentColour = TileColour.opposite(agentColour);

        TileColour t1Colour = board.getTileAtPosition(fromX,fromY).getColour();
        TileColour t2Colour = board.getTileAtPosition(toX,toY).getColour();

        if(t1Colour.equals(nonAgentColour) || t2Colour.equals(nonAgentColour)){
            return;
        }

        double weight = nearZeroWeightConstant;

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
        gridGraph.connectXyWithWeight(b.getX1(),b.getY1(), b.getX2(),b.getY2(),nearZeroWeightConstant+weightConstant/5);
    }

    @Override
    public void connectEnds(GridGraph gridGraph, int x1, int y1, int x2, int y2, TileColour tileColour, TileColour agentColour) {
        double weight = nearZeroWeightConstant;
        if(tileColour == TileColour.opposite(agentColour)){
            return;
        }
        if(tileColour == TileColour.WHITE){
            weight += weightConstant;
        }
        gridGraph.connectXyWithWeight(x1,y1,x2,y2,weight);
    }


}

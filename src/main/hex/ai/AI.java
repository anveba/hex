package main.hex.ai;

import main.hex.Board;
import main.hex.Tile;

import java.util.ArrayList;
import java.util.Optional;

public class AI {
    private final Tile[][] currentState;

    private Tile.Colour agentColour;

    private boolean agentPlaysVertical;
    private Tile.Colour verticalColour;
    private Tile.Colour horizontalColour;

    public AI(Tile[][] currentState,Tile.Colour agentColour, boolean playsVertical){
        this.currentState = currentState;
        this.agentColour = agentColour;
        if(playsVertical){
            verticalColour = agentColour;
            horizontalColour = Tile.opposite(agentColour);
        }
        else {
            verticalColour = Tile.opposite(agentColour);
            horizontalColour = agentColour;
        }
    }

    private Move minimax(Board state, int depth, boolean maximizingPlayer){

        Graph2 g = new Graph2(state,verticalColour,horizontalColour);
        double eval = g.boardEvaluation();

        if(depth == 0 || eval == Double.POSITIVE_INFINITY || eval == Double.NEGATIVE_INFINITY){
            return new Move(-1,eval);
        }

        int moveIndex = 0;

        if (maximizingPlayer){
            Move maxMove = Move.MAX_VALUE_MOVE;

            ArrayList<Board> children = createChildren(state,agentColour);
            for(int i = 0; i<children.size();i++){
                Move iEval = minimax(children.get(i),depth-1,false);
                maxMove = maxMove.max(iEval);
            }

            return maxMove;
        }

        else {
            Move minMove = Move.MIN_VALUE_MOVE;

            ArrayList<Board> children = createChildren(state,Tile.opposite(agentColour));
            for(int i = 0; i<children.size();i++){
                Move iEval = minimax(children.get(i),depth-1,true);
                minMove = minMove.min(iEval);
            }
            return minMove;
        }


    }

    private ArrayList<Board> createChildren(Board parentState,Tile.Colour agentColour){
        int max_no_of_children = parentState.size()*parentState.size();
        ArrayList<Board> children = new ArrayList<>((parentState.size()*parentState.size())/2);
        for(int i = 0; i<max_no_of_children; i++){
            Optional<Board> child = createChild(parentState,agentColour,i);
            child.ifPresent(children::add);
        }
        return children;
    }

    private Optional<Board> createChild(Board parentState,Tile.Colour agentColour,int childNo){

        if(childNo > parentState.size() * parentState.size()){
            throw new AIException("Created too many children");
        }

        int x = childNo % parentState.size();
        int y = childNo / parentState.size();
        if(currentState[x][y].getColour() != Tile.Colour.WHITE){
            return Optional.empty();
        }

        Board childState = parentState.clone();
        childState.getTileAtPosition(x, y).setColour(agentColour);
        return Optional.of(childState);
    }





}

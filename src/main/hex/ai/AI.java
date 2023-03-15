package main.hex.ai;

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

    private Move minimax(Tile[][] state, int depth, boolean maximizingPlayer){

        Graph2 g = new Graph2(state,verticalColour,horizontalColour);
        double eval = g.boardEvaluation();

        if(depth == 0 || eval == Double.POSITIVE_INFINITY || eval == Double.NEGATIVE_INFINITY){
            return new Move(-1,eval);
        }

        int moveIndex = 0;

        if (maximizingPlayer){
            Move maxMove = Move.MAX_VALUE_MOVE;

            ArrayList<Tile[][]> children = createChildren(state,agentColour);
            for(int i = 0; i<children.size();i++){
                Move iEval = minimax(children.get(i),depth-1,false);
                maxMove = maxMove.max(iEval);
            }

            return maxMove;
        }

        else {
            Move minMove = Move.MIN_VALUE_MOVE;

            ArrayList<Tile[][]> children = createChildren(state,Tile.opposite(agentColour));
            for(int i = 0; i<children.size();i++){
                Move iEval = minimax(children.get(i),depth-1,true);
                minMove = minMove.min(iEval);
            }
            return minMove;
        }


    }

    private ArrayList<Tile[][]> createChildren(Tile[][] parentState,Tile.Colour agentColour){
        int max_no_of_children = parentState.length*parentState.length;
        ArrayList<Tile[][]> children = new ArrayList<>((parentState.length*parentState.length)/2);
        for(int i = 0; i<max_no_of_children; i++){
            Optional<Tile[][]> child = createChild(parentState,agentColour,i);
            child.ifPresent(children::add);
        }
        return children;
    }

    private Optional<Tile[][]> createChild(Tile[][] parentState,Tile.Colour agentColour,int childNo){

        if(childNo > parentState.length * parentState.length){
            throw new AIException("Created too many children");
        }

        int x = childNo % parentState.length;
        int y = childNo / parentState.length;
        if(currentState[x][y].getColour() != Tile.Colour.WHITE){
            return  Optional.empty();
        }

        Tile[][] childState = new Tile[parentState.length][parentState.length];
        for(int i = 0; i< parentState.length; i++){
            childState[i] = parentState[i].clone();
        }
        childState[x][y].setColour(agentColour);
        return Optional.of(childState);
    }





}

package main.hex.ai;

import main.hex.Tile;

import java.lang.reflect.Array;
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

    private double minimax(Tile[][] state, int depth, boolean maximizingPlayer){

        Graph g = new Graph(state,verticalColour,horizontalColour);
        double eval = g.boardEvaluation();

        if(depth == 0 || eval == Double.POSITIVE_INFINITY || eval == Double.NEGATIVE_INFINITY){
            return eval;
        }

        if (maximizingPlayer){
            double maxEval = Double.NEGATIVE_INFINITY;

            ArrayList<Tile[][]> children = createChildren(state,agentColour);
            for(Tile[][] c : children){
                maxEval = Double.max(eval,minimax(c,depth-1,false));

            }
            return  maxEval;
        }

        else {
            double minEval = Double.POSITIVE_INFINITY;

            ArrayList<Tile[][]> children = createChildren(state,Tile.opposite(agentColour));
            for(Tile[][] c : children){
                minEval = Double.min(eval,minimax(c,depth-1,false));

            }
            return  minEval;
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

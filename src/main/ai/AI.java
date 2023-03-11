package main.ai;

import main.hex.Tile;

import java.util.ArrayList;
import java.util.Optional;

public class AI {


    private final Tile[][] currentState;
    private int numberOfCreatedChildren;

    private Tile.Colour agentColour;
    private boolean playsVertical;

    public AI(Tile[][] currentState,Tile.Colour agentColour, boolean playsVertical){
        this.currentState = currentState;
        this.numberOfCreatedChildren = 0;
        this.agentColour = agentColour;
        this.playsVertical = playsVertical;
    }

    private float minimax(Tile[][] state, int depth, boolean maximizingPlayer){
        //!TODO
        return 0;
    }



    private Optional<Tile[][]> createNextChild(Tile[][] parentState,Tile.Colour agentColour){

        if(numberOfCreatedChildren > parentState.length * parentState.length){
            return Optional.empty();
        }

        int x = numberOfCreatedChildren % parentState.length;
        int y = numberOfCreatedChildren / parentState.length;
        if(currentState[x][y].getColour() != Tile.Colour.WHITE){
            numberOfCreatedChildren += 1;
            return  createNextChild(parentState,agentColour);
        }

        Tile[][] childState = new Tile[parentState.length][parentState.length];
        for(int i = 0; i< parentState.length; i++){
            childState[i] = parentState[i].clone();
        }
        childState[x][y].setColour(agentColour);
        return Optional.of(childState);
    }





}

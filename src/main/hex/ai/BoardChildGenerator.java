package main.hex.ai;

import main.hex.ai.AIException;
import main.hex.ai.AIMove;
import main.hex.board.Board;
import main.hex.board.TileColour;

import java.util.ArrayList;
import java.util.Optional;

public class BoardChildGenerator {

    public ArrayList<AIMove> createChildren(Board parentState){
        ArrayList<AIMove> children = new ArrayList<>((parentState.size()*parentState.size())/2);
        for(int i = 0; i< parentState.size(); i++){
            for(int j = 0; j< parentState.size(); j++) {
                Optional<AIMove> child = createChildMove(parentState, i,j);
                child.ifPresent(children::add);
            }
        }

        return children;
    }

    public Optional<AIMove> createChildMove(Board parentState, int x, int y){

        if(x >= parentState.size() || y >= parentState.size()){
            throw new AIException("Created too many children");
        }


        if(parentState.getTileAtPosition(x,y).getColour() != TileColour.WHITE){
            return Optional.empty();
        }

        return Optional.of(new AIMove(x,y,0.0,0));
    }
}

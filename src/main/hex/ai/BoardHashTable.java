package main.hex.ai;

import main.hex.Move;
import main.hex.board.Board;
import main.hex.board.TileColour;

import java.util.HashMap;
import java.util.Optional;

public class BoardHashTable {

    private HashMap<String, AIMove> hashMap;

    public BoardHashTable(){
        this.hashMap = new HashMap<>();
    }

    public void putBoard(Board b,AIMove m){
        hashMap.put(hashBoardToString(b),m);
    }

    public Optional<AIMove> getBoard(Board b){
        String key = hashBoardToString(b);
        if(hashMap.containsKey(key)){
            return Optional.of(hashMap.get(key));
        }
        return Optional.empty();
    }

    public boolean containsKey(Board b) {
        return hashMap.containsKey(hashBoardToString(b));
    }


    private String hashBoardToString(Board b){
        StringBuilder hash = new StringBuilder();
        for(int x = 0; x < b.size(); x++){
            for(int y = 0; y < b.size(); y++){
                if(b.getTileAtPosition(x,y).getColour() == TileColour.PLAYER2){
                    hash.append("R");
                    continue;
                }
                if(b.getTileAtPosition(x,y).getColour() == TileColour.PLAYER1){
                    hash.append("B");
                    continue;
                }
                hash.append("W");

            }
        }
        return hash.toString();
    }

}

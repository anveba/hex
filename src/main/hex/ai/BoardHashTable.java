package main.hex.ai;

import main.hex.Board;
import main.hex.Tile;

import java.util.HashMap;
import java.util.Optional;

public class BoardHashTable {

    private HashMap<String ,Move> hashMap;

    public BoardHashTable(){
        this.hashMap = new HashMap<>();
    }

    public void putBoard(Board b,Move m){
        hashMap.put(hashBoardToString(b),m);
    }

    public Optional<Move> getBoard(Board b){
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
                if(b.getTileAtPosition(x,y).getColour() == Tile.Colour.RED){
                    hash.append("R");
                    continue;
                }
                if(b.getTileAtPosition(x,y).getColour() == Tile.Colour.BLUE){
                    hash.append("B");
                    continue;
                }
                hash.append("W");

            }
        }
        return hash.toString();
    }

}

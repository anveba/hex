package main.hex.ai;

import main.hex.Move;
import main.hex.board.Board;
import main.hex.board.TileColour;

import java.util.HashMap;
import java.util.Optional;

public class BoardHashTable {

    private HashMap<Integer, AIMove> hashMap;

    public BoardHashTable(){
        this.hashMap = new HashMap<>();
    }

    public void putBoard(Board b,AIMove m){
        hashMap.put(b.getHash(),m);
    }

    public Optional<AIMove> getBoard(Board b){
        int key = b.getHash();
        if(hashMap.containsKey(key)){
            return Optional.of(hashMap.get(key));
        }
        return Optional.empty();
    }

    public boolean containsKey(Board b) {
        return hashMap.containsKey(b.getHash());
    }



}

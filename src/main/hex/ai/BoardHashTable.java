package main.hex.ai;

import main.hex.Move;
import main.hex.board.Board;
import main.hex.board.TileColour;

import java.util.HashMap;
import java.util.Optional;

/*
Author: Nikolaj
Simply a wrapper class for the Java Hashmap
We use a separate class, because we considered it very likely that we would create our own Hashmap
to optimize the AI, and this allows us to make all the changes we want, as long as it supports the operations in this class
 */

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

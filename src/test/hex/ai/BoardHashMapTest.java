package test.hex.ai;

import main.hex.ai.AIMove;
import main.hex.ai.BoardHashTable;
import main.hex.ai.BoardHasher;
import main.hex.board.Board;
import main.hex.board.Tile;
import main.hex.board.TileColour;
import org.junit.Test;

public class BoardHashMapTest {


    @Test
    public void tableContainsBoardAlreadyInserted(){

        Board b1 = new Board(5);
        b1.setTileAtPosition(new Tile(TileColour.PLAYER2),3,3);

        Board b2 = new Board(5);
        b2.setTileAtPosition(new Tile(TileColour.PLAYER2),3,3);

        BoardHashTable t = new BoardHashTable();
        t.putBoard(b1,new AIMove(0,0,0.0));
        assert(t.containsKey(b2));


    }
}

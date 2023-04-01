package test.hex.ai;

import main.hex.Board;
import main.hex.Tile;
import main.hex.ai.BoardHashTable;
import main.hex.ai.Move;
import org.junit.Test;

public class BoardHasherTester {



    @Test
    public void twoIdenticalBoardsAreHashedTheSame(){

        Board b1 = new Board(5);
        Board b2 = new Board(5);

        BoardHashTable t = new BoardHashTable();
        t.putBoard(b1,new Move(1,1));
        assert(t.containsKey(b2));

    }


    @Test
    public void twoDifferentBoardsAreHashedTheDifferently(){

        Board b1 = new Board(5);
        Board b2 = new Board(5);
        b2.getTileAtPosition(1,1).setColour(Tile.Colour.RED);

        BoardHashTable t = new BoardHashTable();
        t.putBoard(b1,new Move(1,1));
        assert(!t.containsKey(b2));

    }

}

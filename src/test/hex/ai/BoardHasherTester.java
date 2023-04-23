package test.hex.ai;

import main.hex.board.Board;
import main.hex.board.Tile;
import main.hex.board.TileColour;
import main.hex.ai.AIMove;
import main.hex.ai.BoardHashTable;
import org.junit.Test;

public class BoardHasherTester {



    @Test
    public void twoIdenticalBoardsAreHashedTheSame(){

        Board b1 = new Board(5);
        Board b2 = new Board(5);

        BoardHashTable t = new BoardHashTable();
        t.putBoard(b1,new AIMove(1,1, 0.0));
        assert(t.containsKey(b2));

    }


    @Test
    public void twoDifferentBoardsAreHashedTheDifferently(){

        Board b1 = new Board(5);
        Board b2 = new Board(5);
        b2.setTileAtPosition(new Tile(TileColour.PLAYER2), 1, 1);

        BoardHashTable t = new BoardHashTable();
        t.putBoard(b1,new AIMove(1,1, 0.0));
        assert(!t.containsKey(b2));

    }

}

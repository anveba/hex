package test.hex.ai;

import main.hex.ai.BoardHasher;
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

        BoardHasher bh = new BoardHasher(5);
        assert(bh.hash(b1) == bh.hash(b2));

    }


    @Test
    public void twoDifferentBoardsAreHashedTheDifferently(){

        Board b1 = new Board(5);
        b1.setTileAtPosition(new Tile(TileColour.PLAYER2),3,3);
        Board b2 = new Board(5);

        BoardHasher bh = new BoardHasher(5);
        //bh.printZobristTable();
        assert(bh.hash(b1) != bh.hash(b2));

    }

    @Test
    public void twoIdenticalBoardsReachedDifferentlyAreHashedTheSame(){

        Board b1 = new Board(5);
        b1.setTileAtPosition(new Tile(TileColour.PLAYER2),3,3);
        Board b2 = new Board(5);

        BoardHasher bh = new BoardHasher(5);

        int b2Hash = bh.hash(b2);
        assert(bh.hash(b1) == bh.toggleMoveToBoardHash(b2Hash,new AIMove(3,3,0),TileColour.PLAYER2));


    }

}

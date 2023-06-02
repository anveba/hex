package test.hex.ai;

import main.hex.ai.AIMove;
import main.hex.ai.graph.Graph;
import main.hex.board.Board;
import main.hex.board.Tile;
import main.hex.board.TileColour;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class MakeMoveTest {



    @Test
    public void MakingAMoveOnBoardResultsInNewBoardWithTileAddedOnMoveCoordinates(){
        Board b = new Board(5);
        AIMove m = new AIMove(2,3,0,0);
        b.makeMove(m, TileColour.PLAYER1);
        assert(b.getTileAtPosition(2,3).getColour() == TileColour.PLAYER1);
    }

    @Test
    public void UnMakingMoveOnBoardResultsInWhiteOnMoveCoordinates(){
        Board b = new Board(5);
        b.setTileAtPosition(new Tile(TileColour.PLAYER2),2,3);
        AIMove m = new AIMove(2,3,0,0);
        b.unMakeMove(m,TileColour.PLAYER2);
        assert(b.getTileAtPosition(2,3).getColour() == TileColour.WHITE);
    }
}

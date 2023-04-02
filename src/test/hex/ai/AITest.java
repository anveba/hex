package test.hex.ai;

import main.hex.AIPlayer;
import main.hex.Move;
import main.hex.Player;
import main.hex.ai.SignalBasedAI;
import main.hex.board.Board;
import main.hex.board.Tile;
import main.hex.board.TileColour;
import main.hex.ai.AI;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AITest {

    @Test
    public void AIFindsWinningMoveA(){

        Board board = new Board(3);
        board.setTileAtPosition(new Tile(TileColour.BLUE), 0,1);
        board.setTileAtPosition(new Tile(TileColour.BLUE), 2,1);

        Player agent = new AIPlayer(TileColour.BLUE, 1);

        AI ai = new SignalBasedAI(board, agent);

        Move nextMove = ai.getBestMove(2);
        assertEquals(1, nextMove.getX());
        assertEquals(1, nextMove.getY());
    }
}

package test.hex.ai;

import main.hex.AIPlayer;
import main.hex.Move;
import main.hex.Player;

import main.hex.ai.AI;
import main.hex.board.Board;
import main.hex.board.Tile;
import main.hex.board.TileColour;
import test.hex.TestPlayerClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AITest {

    @Test
    public void AIFindsWinningMoveA(){

        Board board = new Board(3);
        board.setTileAtPosition(new Tile(TileColour.BLUE), 0,1);
        board.setTileAtPosition(new Tile(TileColour.BLUE), 2,1);

        Player agent = new AIPlayer(TileColour.BLUE, 1);

        AI ai = new AI(board, agent);

        Move nextMove = ai.getBestMove(2);
        assertEquals(1, nextMove.getX());
        assertEquals(1, nextMove.getY());
    }


    //Basically just a test to see how deep we can run the search
    @Test
    public void AIDepthFunction(){
        Board board = new Board(3);


        Player agent = new TestPlayerClass(TileColour.BLUE);

        AI ai = new AI(board,agent);


        Move nextMove = ai.getBestMove(9);
        //System.out.println(nextMove.getX() + " "+nextMove.getY());
        

    }
}

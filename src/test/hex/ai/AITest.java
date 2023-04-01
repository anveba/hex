package test.hex.ai;

import main.hex.Board;
import main.hex.Player;
import main.hex.Tile;
import main.hex.ai.AI;
import main.hex.ai.BoardEvaluator;
import main.hex.ai.Move;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AITest {




    @Test
    public void AIFindsWinningMoveA(){

        Board board = new Board(3);
        board.getTileAtPosition(0,1).setColour(Tile.Colour.RED);
        board.getTileAtPosition(2,1).setColour(Tile.Colour.RED);

        Player agent = new Player(Tile.Colour.RED,false);

        AI ai = new AI(board,agent);


        Move nextMove = ai.getBestMove(2,agent);
        assertEquals( 1, nextMove.getX());
        assertEquals( 1, nextMove.getY());



    }


    //Basically just a test to see how deep we can run the search
    @Test
    public void AIDepthFunction(){
        Board board = new Board(3);


        Player agent = new Player(Tile.Colour.RED,false);

        AI ai = new AI(board,agent);


        Move nextMove = ai.getBestMove(9,agent);
        //System.out.println(nextMove.getX() + " "+nextMove.getY());
        

    }
}

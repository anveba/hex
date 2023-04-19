package test.hex.ai;

import main.engine.graphics.Colour;
import main.hex.AIPlayer;
import main.hex.Move;
import main.hex.Player;

import main.hex.ai.AI;
import main.hex.ai.AIMove;
import main.hex.ai.graph.BoardEvaluator;
import main.hex.ai.graph.connectionFunctions.DijkstraBasedTileConnector;
import main.hex.ai.graph.heuristicFunctions.DijkstraGraphHeuristic;
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


    /*
    Test not completed yet
    @Test
    public void AIFindsWinningMoveB(){

        Board board = new Board(3);
        board.setTileAtPosition(new Tile(TileColour.BLUE), 0,0);
        board.setTileAtPosition(new Tile(TileColour.BLUE), 1,0);
        board.setTileAtPosition(new Tile(TileColour.BLUE), 2,0);

        board.setTileAtPosition(new Tile(TileColour.RED), 1,2);
        board.setTileAtPosition(new Tile(TileColour.RED), 2,2);
        board.setTileAtPosition(new Tile(TileColour.RED), 2,1);

        Player agent = new AIPlayer(TileColour.RED, 1);

        AI ai = new AI(board, agent);

        Move nextMove = ai.getBestMove(2);
        assertEquals(1, nextMove.getX());
        assertEquals(1, nextMove.getY());
    }


     */

    @Test
    //Test for a bug found, where the AI would not play a valid move on a board state where it should
    public void AIMakesValidMoveInGivenStateA(){
        Board board = new Board(3);
        board.setTileAtPosition(new Tile(TileColour.BLUE), 0,2);
        board.setTileAtPosition(new Tile(TileColour.BLUE), 1,1);
        board.setTileAtPosition(new Tile(TileColour.BLUE), 2,0);

        board.setTileAtPosition(new Tile(TileColour.RED), 2,2);
        board.setTileAtPosition(new Tile(TileColour.RED), 2,1);
        board.setTileAtPosition(new Tile(TileColour.RED), 1,2);

        Player agent = new AIPlayer(TileColour.RED, 1);

        AI ai = new AI(board, agent);

        AIMove nextMove = ai.getBestMove(1);
        //System.out.println(nextMove.getValue());
        assert(board.getTileAtPosition(nextMove.getX(), nextMove.getY()).getColour().equals(TileColour.WHITE));
    }

    @Test
    //Test for ai prioritizing winning in its own direction
    public void AIFindsWinningMoveC(){
        Board board = new Board(3);
        board.setTileAtPosition(new Tile(TileColour.BLUE), 0,0);
        board.setTileAtPosition(new Tile(TileColour.BLUE), 1,1);
        board.setTileAtPosition(new Tile(TileColour.BLUE), 0,1);
        board.setTileAtPosition(new Tile(TileColour.BLUE), 1,0);

        board.setTileAtPosition(new Tile(TileColour.RED), 2,2);
        board.setTileAtPosition(new Tile(TileColour.RED), 2,1);
        board.setTileAtPosition(new Tile(TileColour.RED), 1,2);

        Board board1 = board.clone();
        Board board2 = board.clone();

        board1.setTileAtPosition(new Tile(TileColour.RED), 2,0);
        board1.setTileAtPosition(new Tile(TileColour.RED), 0,2);

        BoardEvaluator boardEvaluator1 = new BoardEvaluator(board1,TileColour.RED,TileColour.BLUE,new DijkstraBasedTileConnector(), new DijkstraGraphHeuristic());
        BoardEvaluator boardEvaluator2 = new BoardEvaluator(board2,TileColour.RED,TileColour.BLUE,new DijkstraBasedTileConnector(), new DijkstraGraphHeuristic());

        System.out.println(boardEvaluator1.evaluateBoard());
        System.out.println(boardEvaluator2.evaluateBoard());

        Player agent = new AIPlayer(TileColour.RED, 1);
        //System.out.println(agent.winsByVerticalConnection());

        AI ai = new AI(board, agent);

        AIMove nextMove = ai.getBestMove(1);
        System.out.println(nextMove.getValue());
        assert(nextMove.getX() == 2);
        assert(nextMove.getY() == 0);
    }



    @Test
    //Test for ai prioritizing winning in its own direction
    public void AIFindsWinningMoveD(){
        Board board = new Board(3);
        board.setTileAtPosition(new Tile(TileColour.RED), 0,0);
        board.setTileAtPosition(new Tile(TileColour.RED), 1,1);
        board.setTileAtPosition(new Tile(TileColour.RED), 0,1);
        board.setTileAtPosition(new Tile(TileColour.RED), 1,0);

        board.setTileAtPosition(new Tile(TileColour.BLUE), 2,2);
        board.setTileAtPosition(new Tile(TileColour.BLUE), 2,1);
        board.setTileAtPosition(new Tile(TileColour.BLUE), 1,2);

        Player agent = new AIPlayer(TileColour.BLUE, 1);

        AI ai = new AI(board, agent);

        AIMove nextMove = ai.getBestMove(1);
        //System.out.println(nextMove.getValue());
        //System.out.println(nextMove.getX());
        assert(nextMove.getX() == 0);
        assert(nextMove.getY() == 2);
    }


    @Test
    //Test for a bug found, where the AI would not play a valid move on a board state where it should
    //This bug is resolved in a different way
    public void AIMakesValidMoveInGivenStateB(){
        Board board = new Board(3);
        board.setTileAtPosition(new Tile(TileColour.RED), 0,2);
        board.setTileAtPosition(new Tile(TileColour.RED),1,1);
        board.setTileAtPosition(new Tile(TileColour.RED), 2,1);
        board.setTileAtPosition(new Tile(TileColour.RED), 0,0);

        board.setTileAtPosition(new Tile(TileColour.BLUE), 2,2);
        board.setTileAtPosition(new Tile(TileColour.BLUE), 1,2);
        board.setTileAtPosition(new Tile(TileColour.BLUE), 2,0);
        board.setTileAtPosition(new Tile(TileColour.BLUE), 1,0);

        Player agent = new AIPlayer(TileColour.RED, 1);

        AI ai = new AI(board, agent);

        AIMove nextMove = ai.getBestMove(1);
        //System.out.println(nextMove.getValue());
        assert(board.getTileAtPosition(nextMove.getX(), nextMove.getY()).getColour().equals(TileColour.WHITE));
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
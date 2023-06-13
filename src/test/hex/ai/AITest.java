package test.hex.ai;

import main.hex.Move;
import main.hex.ai.AI;
import main.hex.ai.AIMove;
import main.hex.ai.graph.BoardEvaluator;
import main.hex.ai.graph.connectionFunctions.DijkstraBasedTileConnector;
import main.hex.ai.graph.heuristicFunctions.DijkstraGraphHeuristic;
import main.hex.board.Board;
import main.hex.board.Tile;
import main.hex.board.TileColour;
import main.hex.player.AIPlayer;
import main.hex.player.Player;

import org.junit.Ignore;
import test.hex.TestPlayerClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AITest {

    @Test
    public void AIFindsWinningMoveA(){

        Board board = new Board(3);
        board.setTileAtPosition(new Tile(TileColour.PLAYER1), 0,1);
        board.setTileAtPosition(new Tile(TileColour.PLAYER1), 2,1);

        Player agent = new AIPlayer(TileColour.PLAYER1, 10.0f, 1, "AI");

        AI ai = new AI(board, agent);

        Move nextMove = ai.getBestMoveWithDepth(2);
        assertEquals(1, nextMove.getX());
        assertEquals(1, nextMove.getY());
    }




    @Test
    //Test for a bug found, where the AI would not play a valid move on a board state where it should
    public void AIMakesValidMoveInGivenStateA(){
        Board board = new Board(3);
        board.setTileAtPosition(new Tile(TileColour.PLAYER1), 0,2);
        board.setTileAtPosition(new Tile(TileColour.PLAYER1), 1,1);
        board.setTileAtPosition(new Tile(TileColour.PLAYER1), 2,0);

        board.setTileAtPosition(new Tile(TileColour.PLAYER2), 2,2);
        board.setTileAtPosition(new Tile(TileColour.PLAYER2), 2,1);
        board.setTileAtPosition(new Tile(TileColour.PLAYER2), 1,2);

        Player agent = new AIPlayer(TileColour.PLAYER2, 10.0f, 1, "AI");

        AI ai = new AI(board, agent);

        AIMove nextMove = ai.getBestMoveWithDepth(1);
        //System.out.println(nextMove.getValue());
        assertEquals(board.getTileAtPosition(nextMove.getX(), nextMove.getY()).getColour(), TileColour.WHITE);
    }

    @Test
    //Test for ai prioritizing winning in its own direction
    public void AIFindsWinningMoveC(){
        Board board = new Board(3);
        board.setTileAtPosition(new Tile(TileColour.PLAYER1), 0,0);
        board.setTileAtPosition(new Tile(TileColour.PLAYER1), 1,1);
        board.setTileAtPosition(new Tile(TileColour.PLAYER1), 0,1);
        board.setTileAtPosition(new Tile(TileColour.PLAYER1), 1,0);

        board.setTileAtPosition(new Tile(TileColour.PLAYER2), 2,2);
        board.setTileAtPosition(new Tile(TileColour.PLAYER2), 2,1);
        board.setTileAtPosition(new Tile(TileColour.PLAYER2), 1,2);

        Board board1 = board.clone();
        Board board2 = board.clone();

        board1.setTileAtPosition(new Tile(TileColour.PLAYER2), 2,0);
        board1.setTileAtPosition(new Tile(TileColour.PLAYER2), 0,2);

        BoardEvaluator boardEvaluator1 = new BoardEvaluator(board1,TileColour.PLAYER2,TileColour.PLAYER1,new DijkstraBasedTileConnector(), new DijkstraGraphHeuristic());
        BoardEvaluator boardEvaluator2 = new BoardEvaluator(board2,TileColour.PLAYER2,TileColour.PLAYER1,new DijkstraBasedTileConnector(), new DijkstraGraphHeuristic());

        System.out.println(boardEvaluator1.evaluateBoard());
        System.out.println(boardEvaluator2.evaluateBoard());

        Player agent = new AIPlayer(TileColour.PLAYER2, 10.0f, 1, "AI");
        //System.out.println(agent.winsByVerticalConnection());

        AI ai = new AI(board, agent);

        AIMove nextMove = ai.getBestMoveWithDepth(1);
        System.out.println(nextMove.getValue());
        assertEquals(2, nextMove.getX());
        assertEquals(0, nextMove.getY());
    }



    @Test
    //Test for ai prioritizing winning in its own direction
    public void AIFindsWinningMoveD(){
        Board board = new Board(3);
        board.setTileAtPosition(new Tile(TileColour.PLAYER2), 0,0);
        board.setTileAtPosition(new Tile(TileColour.PLAYER2), 1,1);
        board.setTileAtPosition(new Tile(TileColour.PLAYER2), 0,1);
        board.setTileAtPosition(new Tile(TileColour.PLAYER2), 1,0);

        board.setTileAtPosition(new Tile(TileColour.PLAYER1), 2,2);
        board.setTileAtPosition(new Tile(TileColour.PLAYER1), 2,1);
        board.setTileAtPosition(new Tile(TileColour.PLAYER1), 1,2);

        Player agent = new AIPlayer(TileColour.PLAYER1, 10.0f, 1, "AI");

        AI ai = new AI(board, agent);

        AIMove nextMove = ai.getBestMoveWithDepth(1);
        //System.out.println(nextMove.getValue());
        //System.out.println(nextMove.getX());
        assertEquals(0, nextMove.getX());
        assertEquals(nextMove.getY(), 2);
    }

    //During play-testing, the AI seemed to make a weird choice in this particular state.
    @Ignore
    public void AIFindsNonLosingMoveA(){
        Board board = new Board(6);
        board.setTileAtPosition(new Tile(TileColour.PLAYER1), 0,0);
        board.setTileAtPosition(new Tile(TileColour.PLAYER1), 2,1);
        board.setTileAtPosition(new Tile(TileColour.PLAYER1), 1,2);
        board.setTileAtPosition(new Tile(TileColour.PLAYER1), 2,5);
        board.setTileAtPosition(new Tile(TileColour.PLAYER1), 3,3);
        board.setTileAtPosition(new Tile(TileColour.PLAYER1), 4,5);

        board.setTileAtPosition(new Tile(TileColour.PLAYER2), 1,0);
        board.setTileAtPosition(new Tile(TileColour.PLAYER2), 1,1);
        board.setTileAtPosition(new Tile(TileColour.PLAYER2), 3,2);
        board.setTileAtPosition(new Tile(TileColour.PLAYER2), 2,3);
        board.setTileAtPosition(new Tile(TileColour.PLAYER2), 2,4);
        board.setTileAtPosition(new Tile(TileColour.PLAYER2), 3,5);


        Player agent = new AIPlayer(TileColour.PLAYER1, 10.0f, 5.0f, "AI");

        AI ai = new AI(board, agent);
        AIMove m = ai.getBestMoveWithTimeLimit(10.0f);
        System.out.println(m.getValue());
        assertTrue("AI picked a move that allowed opponent to win in next turn: "+m.getX()+", "+m.getY(),m.getX() == 2 && m.getY() == 2);

    }

    @Ignore
    public void AIDFindsWinningMove(){
        Board board = new Board(6);
        board.setTileAtPosition(new Tile(TileColour.PLAYER1), 0,0);
        board.setTileAtPosition(new Tile(TileColour.PLAYER1), 2,1);
        board.setTileAtPosition(new Tile(TileColour.PLAYER1), 1,2);
        board.setTileAtPosition(new Tile(TileColour.PLAYER1), 2,5);
        board.setTileAtPosition(new Tile(TileColour.PLAYER1), 3,3);
        board.setTileAtPosition(new Tile(TileColour.PLAYER1), 4,5);
        board.setTileAtPosition(new Tile(TileColour.PLAYER1), 2,0);

        board.setTileAtPosition(new Tile(TileColour.PLAYER2), 1,0);
        board.setTileAtPosition(new Tile(TileColour.PLAYER2), 1,1);
        board.setTileAtPosition(new Tile(TileColour.PLAYER2), 3,2);
        board.setTileAtPosition(new Tile(TileColour.PLAYER2), 2,3);
        board.setTileAtPosition(new Tile(TileColour.PLAYER2), 2,4);
        board.setTileAtPosition(new Tile(TileColour.PLAYER2), 3,5);


        Player agent = new AIPlayer(TileColour.PLAYER2, 10.0f, 5.0f, "AI");

        AI ai = new AI(board, agent);
        AIMove m = ai.getBestMoveWithTimeLimit(10.0f);
        System.out.println(m.getValue());
        assertTrue("AI picked a move that allowed opponent to win in next turn: "+m.getX()+", "+m.getY(),m.getX() == 2 && m.getY() == 2);
    }

    @Test
    //Test for a bug found, where the AI would not play a valid move on a board state where it should
    //This bug is resolved in a different way
    public void AIMakesValidMoveInGivenStateB(){
        Board board = new Board(3);
        board.setTileAtPosition(new Tile(TileColour.PLAYER2), 0,2);
        board.setTileAtPosition(new Tile(TileColour.PLAYER2),1,1);
        board.setTileAtPosition(new Tile(TileColour.PLAYER2), 2,1);
        board.setTileAtPosition(new Tile(TileColour.PLAYER2), 0,0);

        board.setTileAtPosition(new Tile(TileColour.PLAYER1), 2,2);
        board.setTileAtPosition(new Tile(TileColour.PLAYER1), 1,2);
        board.setTileAtPosition(new Tile(TileColour.PLAYER1), 2,0);
        board.setTileAtPosition(new Tile(TileColour.PLAYER1), 1,0);

        Player agent = new AIPlayer(TileColour.PLAYER2, 10.0f, 1.0f, "AI");

        AI ai = new AI(board, agent);

        AIMove nextMove = ai.getBestMoveWithDepth(1);
        //System.out.println(nextMove.getValue());
        assertEquals(board.getTileAtPosition(nextMove.getX(), nextMove.getY()).getColour(), TileColour.WHITE);
    }


    @Ignore
    @Test
    public void AIFindsDifferentMovesWithDifferentTimeLimits(){
        Player agent = new TestPlayerClass(TileColour.PLAYER1);
        Board board = new Board(5);

        AI ai = new AI(board,agent);

        Move d0 = ai.getBestMoveWithDepth(1);
        Move t0 = ai.getBestMoveWithTimeLimit(0);
        Move t1 = ai.getBestMoveWithTimeLimit(1);

        System.out.println(d0.getX()+ ","+d0.getY());

        assertTrue(d0.getX() == t0.getX() && d0.getY() == t1.getY());
        assertTrue(t0.getX() != t1.getX() || t0.getY() != t1.getY());
    }


    //Basically just a test to see how deep we can run the search
    //Is used more for debugging purposes than an actual test to pass/fail
    @Test
    @Ignore
    public void AIDepthFunction(){
        Board board = new Board(6);


        Player agent = new TestPlayerClass(TileColour.PLAYER1);

        AI ai = new AI(board,agent);

        long startTime = System.nanoTime();
        Move nextMove = ai.getBestMoveWithDepth(5);
        long endTime = System.nanoTime();
        System.out.println((endTime - startTime)/1000000000 + "s");
        System.out.println(BoardEvaluator.getEvaluationCount());
        System.out.println("");
        BoardEvaluator.resetEvaluationCount();

        ai = new AI(board,agent);
        ai.setDoMoveSorting(false);
        startTime = System.nanoTime();
        nextMove = ai.getBestMoveWithDepth(5);
        endTime = System.nanoTime();

        System.out.println((endTime - startTime)/1000000000 + "s");
        System.out.println(BoardEvaluator.getEvaluationCount());

        //System.out.println(nextMove.getX() + " "+nextMove.getY());
        

    }
    //Is used more for debugging purposes than an actual test to pass/fail
    @Test
    @Ignore
    public void AITimeDepthFunction(){
        Board board = new Board(6);
        int timelimit = 5;

        Player agent = new TestPlayerClass(TileColour.PLAYER1);

        AI ai = new AI(board,agent);

        long startTime = System.nanoTime();
        Move nextMove = ai.getBestMoveWithTimeLimit(timelimit);
        long endTime = System.nanoTime();
        System.out.println("With ordering");
        System.out.println((endTime - startTime)/1000000000 + "s");
        System.out.println(BoardEvaluator.getEvaluationCount());
        System.out.println("");
        BoardEvaluator.resetEvaluationCount();

        ai = new AI(board,agent);
        ai.setDoMoveSorting(false);
        System.out.println("No move ordering");
        startTime = System.nanoTime();
        nextMove = ai.getBestMoveWithTimeLimit(timelimit);
        endTime = System.nanoTime();

        System.out.println((endTime - startTime)/1000000000 + "s");
        System.out.println(BoardEvaluator.getEvaluationCount());

        //System.out.println(nextMove.getX() + " "+nextMove.getY());


    }

    @Test
    public void AITimedSearchDoesNotGoOverTime(){
        Board board = new Board(10);


        Player agent = new TestPlayerClass(TileColour.PLAYER1);

        AI ai = new AI(board,agent);

        long t1 = System.currentTimeMillis();
        ai.getBestMoveWithTimeLimit(1);
        long t2 = System.currentTimeMillis();
        assertTrue(t2-t1 < 1100);

        //System.out.println(nextMove.getX() + " "+nextMove.getY());


    }




}

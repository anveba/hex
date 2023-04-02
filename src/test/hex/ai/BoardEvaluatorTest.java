package test.hex.ai;

import main.hex.ai.BoardEvaluator;
import main.hex.board.Board;
import main.hex.board.Tile;
import main.hex.board.TileColour;

import org.junit.Test;

import static org.junit.Assert.*;

public class BoardEvaluatorTest {


    @Test
    public void boardEvaluatorConstructorWorks(){

        Board board = new Board(5);
        BoardEvaluator b = new BoardEvaluator(board,TileColour.BLUE,TileColour.RED);
    }


    @Test
    public void agentColouredTilesAreConnectedWithMaxFadeAfterColourConnection(){
        Board board = new Board(5);
        board.setTileAtPosition(new Tile(TileColour.RED), 1,1);
        board.setTileAtPosition(new Tile(TileColour.RED), 1,2);
        BoardEvaluator  b = new BoardEvaluator(board,TileColour.BLUE,TileColour.RED);
        b.connectByColour(1,1,1,2, TileColour.RED);
        assertEquals(1.0, b.fadeOfAdjacencyXY(1, 1, 1, 2).get(), 0.0);
    }


    /*
    Ranking of fade should be:
    AGENT COLOUR-AGENT COLOUR -> 1
    AGENT COLOUR-WHITE
    WHITE-WHITE
    Any that contains non-agentcolour -> No connection
    */

    @Test
    public void colourPairsGetConnectedSuchThatTheirFadeTheyRankCorrectly(){
        int k = 7;
        Board board = new Board(k);

        board.setTileAtPosition(new Tile(TileColour.RED), 3,0);
        board.setTileAtPosition(new Tile(TileColour.RED), 2,0);
        board.setTileAtPosition(new Tile(TileColour.BLUE), 4,0);
        board.setTileAtPosition(new Tile(TileColour.BLUE), 5,0);

        BoardEvaluator  b = new BoardEvaluator(board,TileColour.BLUE,TileColour.RED);

        for(int i = 0; i<k-1;i++){
            b.connectByColour(i,0,i+1,0, TileColour.RED);
        }

        double c1 = b.fadeOfAdjacencyXY(0, 0, 1, 0).get();
        double c2 = b.fadeOfAdjacencyXY(1, 0, 2, 0).get();
        double c3 = b.fadeOfAdjacencyXY(2, 0, 3, 0).get();


        assertTrue(c1 < c2);
        assertTrue(c2 < c3);

        assertTrue( b.fadeOfAdjacencyXY(3, 0, 4, 0).isEmpty());
        assertTrue( b.fadeOfAdjacencyXY(4, 0, 5, 0).isEmpty());
        assertTrue( b.fadeOfAdjacencyXY(5, 0, 6, 0).isEmpty());


    }


    @Test
    public void AgentColouredNeighboursHaveMaxFadeConnectionAfterHorizontalEvaluationConnection(){
        int k = 2;
        Board board = new Board(k);
        for(int i = 0; i<k;i++){
            for(int j = 0; j<k;j++){
                board.setTileAtPosition(new Tile(TileColour.RED), i,j);
            }
        }
        BoardEvaluator  b = new BoardEvaluator(board,TileColour.BLUE,TileColour.RED);
        b.connectHorizontalEvaluation();
        assertTrue(b.fadeOfAdjacencyXY(0,0,0,1).isPresent());
        assertEquals(1.0, b.fadeOfAdjacencyXY(0, 0, 0, 1).get(), 0.0);
    }

    @Test
    public void AgentColouredNeighboursHaveMaxFadeConnectionAfterVerticalEvaluationConnection(){
        int k = 2;
        Board board = new Board(k);
        for(int i = 0; i<k;i++){
            for(int j = 0; j<k;j++){
                board.setTileAtPosition(new Tile(TileColour.BLUE), i,j);
            }
        }
        BoardEvaluator  b = new BoardEvaluator(board,TileColour.BLUE,TileColour.RED);
        b.connectVerticalEvaluation();
        assertTrue(b.fadeOfAdjacencyXY(0,0,0,1).isPresent());
        assertEquals(1.0, b.fadeOfAdjacencyXY(0, 0, 0, 1).get(), 0.0);
    }



    @Test
    public void differentBoardStatesGetEvaluatedCorrectlyRelativeToEachOther(){
        int k = 5;
        Board board = new Board(k);

        BoardEvaluator b = new BoardEvaluator(board,TileColour.BLUE,TileColour.RED);
        double e1 = b.evaluateBoard();


        Board board2 = new Board(k);
        for(int i = 0; i<k;i++){
            for(int j = 0; j<k;j++){
                board2.setTileAtPosition(new Tile(TileColour.BLUE), i,j);
            }
        }

        BoardEvaluator b2 = new BoardEvaluator(board2,TileColour.BLUE,TileColour.RED);
        double e2 = b2.evaluateBoard();

        Board board3 = new Board(k);
        for(int i = 0; i<k;i++){
            for(int j = 0; j<k;j++){
                board3.setTileAtPosition(new Tile(TileColour.RED), i,j);
            }
        }

        BoardEvaluator b3 = new BoardEvaluator(board3,TileColour.BLUE,TileColour.RED);
        double e3 = b3.evaluateBoard();


        assertTrue(e1 == 0.0);
        assertTrue(e2 > 0);
        assertTrue(e3 < 0);

    }
    @Test
    public void gameWonVertically_whenConnectedVertically(){
        int k = 5;
        Board board = new Board(k);
        for(int i = 0; i<k; i++){
            board.setTileAtPosition(new Tile(TileColour.BLUE), 3,i);
        }

        BoardEvaluator g = new BoardEvaluator(board,TileColour.BLUE,TileColour.RED);
        assertTrue(g.hasWonVertically());

    }

    @Test
    public void gameNotWonVertically_whenNotConnectedVertically(){
        int k = 5;
        Board board = new Board(k);


        BoardEvaluator b = new BoardEvaluator(board,TileColour.BLUE,TileColour.RED);
        assertFalse(b.hasWonVertically());

    }

    @Test
    public void gameWonHorizontally_whenConnectedHorizontally(){
        int k = 5;
        Board board = new Board(k);
        for(int i = 0; i<k; i++){
            board.setTileAtPosition(new Tile(TileColour.RED), i,3);
        }

        BoardEvaluator b = new BoardEvaluator(board, TileColour.BLUE,TileColour.RED);
        assertTrue(b.hasWonHorizontally());

    }

    @Test
    public void gameNotWonHorizontally_whenNotConnectedHorizontally(){
        int k = 5;
        Board board = new Board(k);

        BoardEvaluator b = new BoardEvaluator(board, TileColour.BLUE,TileColour.RED);
        assertFalse(b.hasWonHorizontally());

    }


}

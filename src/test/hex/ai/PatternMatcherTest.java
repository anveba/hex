package test.hex.ai;

import main.hex.ai.AIMove;
import main.hex.ai.BoardChildGenerator;
import main.hex.ai.PatternMatcher;
import main.hex.board.Board;
import main.hex.board.Tile;
import main.hex.board.TileColour;
import main.hex.player.Player;
import org.junit.Test;
import test.hex.TestPlayerClass;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class PatternMatcherTest {

    @Test
    public void borderBridgeDominatedCellsGetRemovedFromEmptyBoardWithHorizontalPlayer(){
        Board b = new Board(5);

        BoardChildGenerator bcg = new BoardChildGenerator();
        Player agent = new TestPlayerClass(TileColour.PLAYER1);



        ArrayList<AIMove> moves = bcg.createChildren(b);

        moves = PatternMatcher.pruneByPatterns(moves,b,agent.getColour());
        for (AIMove m: moves
             ) { assertTrue("Failed with values: x: "+m.getX() + " y: "+m.getY(),m.getX() != 0 && m.getX() != b.size()-1);

        }

    }

    @Test
    public void borderBridgeDominatedCellsGetRemovedFromEmptyBoardWithVerticalPlayer(){
        Board b = new Board(5);
        PatternMatcher p = new PatternMatcher();
        BoardChildGenerator bcg = new BoardChildGenerator();
        Player agent = new TestPlayerClass(TileColour.PLAYER2);


        ArrayList<AIMove> moves = bcg.createChildren(b);
        moves = p.pruneByPatterns(moves,b,agent.getColour());
        for (AIMove m: moves
        ) { assertTrue("Failed with values: x: "+m.getX() + " y: "+m.getY(),m.getY() != 0 && m.getY() != b.size()-1);

        }
        assertTrue(moves.size() > 0);
    }

    @Test
    public void deadCellByCageGetsRemoved(){
        Board b = new Board(8);

        BoardChildGenerator bcg = new BoardChildGenerator();
        Player agent = new TestPlayerClass(TileColour.PLAYER2);

        b.setTileAtPosition(new Tile(TileColour.PLAYER2),5,5);
        b.setTileAtPosition(new Tile(TileColour.PLAYER2),5,6);
        b.setTileAtPosition(new Tile(TileColour.PLAYER2),6,7);
        b.setTileAtPosition(new Tile(TileColour.PLAYER2),5,7);

        ArrayList<AIMove> moves = bcg.createChildren(b);
        moves = PatternMatcher.pruneByPatterns(moves,b,agent.getColour());
        for (AIMove m: moves
        ) { assertTrue("Failed with values: x: "+m.getX() + " y: "+m.getY(),m.getX() != 5 || m.getY() !=6 );

        }
        assertTrue(moves.size() > 0);
    }

    @Test
    public void deadCellByCageOpposingPairsGetRemoved(){
        Board b = new Board(8);

        BoardChildGenerator bcg = new BoardChildGenerator();
        Player agent = new TestPlayerClass(TileColour.PLAYER2);

        b.setTileAtPosition(new Tile(TileColour.PLAYER2),5,5);
        b.setTileAtPosition(new Tile(TileColour.PLAYER2),6,5);
        b.setTileAtPosition(new Tile(TileColour.PLAYER1),4,3);
        b.setTileAtPosition(new Tile(TileColour.PLAYER1),5,3);

        ArrayList<AIMove> moves = bcg.createChildren(b);
        moves = PatternMatcher.pruneByPatterns(moves,b,agent.getColour());
        for (AIMove m: moves
        ) { assertTrue("Failed with values: x: "+m.getX() + " y: "+m.getY(),m.getX() != 5 || m.getY() !=4 );

        }
        assertTrue(moves.size() > 0);
    }

    @Test
    public void nonMatchingPairsDoNotGetPrunedA(){
        Board board = new Board(8);

        BoardChildGenerator bcg = new BoardChildGenerator();
        Player agent = new TestPlayerClass(TileColour.PLAYER2);

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

        ArrayList<AIMove> moves = bcg.createChildren(board);
        moves = PatternMatcher.pruneByPatterns(moves,board,agent.getColour());
        assertTrue("Non-pruneable move got removed",moves.stream().anyMatch(m -> m.getX() == 2 && m.getY() == 2));
    }

    @Test
    public void nonMatchingPairsDoNotGetPrunedB(){
        Board b = new Board(8);

        BoardChildGenerator bcg = new BoardChildGenerator();
        Player agent = new TestPlayerClass(TileColour.PLAYER2);

        int x = 5;
        int y = 6;

        b.setTileAtPosition(new Tile(TileColour.PLAYER1),x-1,y);
        b.setTileAtPosition(new Tile(TileColour.PLAYER2),x-1,y-1);
        b.setTileAtPosition(new Tile(TileColour.PLAYER1),x,y-1);
        b.setTileAtPosition(new Tile(TileColour.PLAYER2),x+1,y);
        b.setTileAtPosition(new Tile(TileColour.PLAYER1),x+1,y+1);
        b.setTileAtPosition(new Tile(TileColour.PLAYER2),x,y-1);

        ArrayList<AIMove> moves = bcg.createChildren(b);
        moves = PatternMatcher.pruneByPatterns(moves,b,agent.getColour());
        assertTrue("Non-pruneable move got removed",moves.stream().anyMatch(m -> m.getX() == x && m.getY() == y));
    }

    @Test
    public void borderMoveDoesNotGetPrunesWhenBridgeCeilingIsColoured(){

        Board b = new Board(8);

        BoardChildGenerator bcg = new BoardChildGenerator();
        Player agent = new TestPlayerClass(TileColour.PLAYER2);



        b.setTileAtPosition(new Tile(TileColour.PLAYER2),3,1);
        b.setTileAtPosition(new Tile(TileColour.PLAYER2),2,1);


        ArrayList<AIMove> moves = bcg.createChildren(b);
        moves = PatternMatcher.pruneByPatterns(moves,b,agent.getColour());
        assertTrue("Non-pruneable move got removed",moves.stream().anyMatch(m -> m.getX() == 2 && m.getY() == 0));
    }
}

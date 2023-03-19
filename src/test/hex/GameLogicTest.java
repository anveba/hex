package test.hex;

import main.hex.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.*;

public class GameLogicTest {
    private Player player1, player2;
    private GameLogic gameLogic;
    private Board board;

    @Before
    public void setup() {
        board = new Board(11);
        player1 = new Player(Tile.Colour.BLUE, true);
        player2 = new Player(Tile.Colour.RED, false);
        gameLogic = new GameLogic(board, player1, player2);
    }

    @Test
    public void createPlayers_firstPlayerTurn_player1() {
        assertEquals(player1, gameLogic.getCurrentTurnsPlayer());
    }

    @Test
    public void nextPlayer_currentlyPlayer1_player2() {
        gameLogic.nextTurn();
        assertEquals(player2, gameLogic.getCurrentTurnsPlayer());
    }

    @Test
    public void nextPlayer_runTwiceCurrentlyPlayer1_player1() { // Checks that players are looped
        gameLogic.nextTurn();
        gameLogic.nextTurn();
        assertEquals(player1, gameLogic.getCurrentTurnsPlayer());
    }

    @Test
    public void swapRule_playerSwap_switchedColours() {
        Tile.Colour p1StartCol = player1.getPlayerColour();
        Tile.Colour p2StartCol = player2.getPlayerColour();
        gameLogic.swapPlayerColours();
        assertEquals(p1StartCol, player2.getPlayerColour());
        assertEquals(p2StartCol, player1.getPlayerColour());
    }
    
    @Test
    public void whiteBoardIsNotAWinForBothPlayers() {
    	assertFalse(gameLogic.playerHasWon(player1));
    	assertFalse(gameLogic.playerHasWon(player2));
    }
    
    @Test
    public void blueVerticalConnectionFromTopToBottomIsAWinForBlue() {
    	for(int i = 0; i < board.getBoardSize(); i++)
    		board.getTileAtPosition(0, i).setColour(Tile.Colour.BLUE);
    	assertTrue(gameLogic.playerHasWon(player1));
    }
    
    @Test
    public void redVerticalConnectionFromTopToBottomIsNotAWinForRed() {
    	for(int i = 0; i < board.getBoardSize(); i++)
    		board.getTileAtPosition(0, i).setColour(Tile.Colour.RED);
    	assertFalse(gameLogic.playerHasWon(player2));
    }
    
    @Test
    public void blueHorizontalConnectionFromTopToBottomIsAWinForBlue() {
    	for(int i = 0; i < board.getBoardSize(); i++)
    		board.getTileAtPosition(i, 0).setColour(Tile.Colour.BLUE);
    	assertFalse(gameLogic.playerHasWon(player1));
    }
    
    @Test
    public void redHorizontalConnectionFromLeftToRightIsNotAWinForRed() {
    	for(int i = 0; i < board.getBoardSize(); i++)
    		board.getTileAtPosition(i, 0).setColour(Tile.Colour.RED);
    	assertTrue(gameLogic.playerHasWon(player2));
    }
    
    @Test
    public void boardWithCenterFilledWithBlueAndWithBlueConnectionFromTopToBottomIsAWinForBlueAndNotAWinForRed() {
    	for(int i = 1; i < board.getBoardSize() - 1; i ++) {
    		for (int j = 1; j < board.getBoardSize() - 1; j++) {
    			board.getTileAtPosition(i, j).setColour(Tile.Colour.BLUE);
    		}
    	}
    	
    	board.getTileAtPosition(1, 0).setColour(Tile.Colour.BLUE);
    	board.getTileAtPosition(
    			board.getBoardSize() - 2, board.getBoardSize() - 1)
    	.setColour(Tile.Colour.BLUE);
    	
    	assertTrue(gameLogic.playerHasWon(player1));
    	assertFalse(gameLogic.playerHasWon(player2));
    }
    
    @Test
    public void blueDiagonalConnectionFromTopRightToBottomLeftIsAWinForBlue() {
    	for(int i = 0; i < board.getBoardSize(); i++)
    		board.getTileAtPosition(i, i).setColour(Tile.Colour.BLUE);
    	assertTrue(gameLogic.playerHasWon(player1));
    }
    
    @Test
    public void redDiagonalConnectionFromTopRightToBottomLeftIsAWinForRed() {
    	for(int i = 0; i < board.getBoardSize(); i++)
    		board.getTileAtPosition(i, i).setColour(Tile.Colour.RED);
    	assertTrue(gameLogic.playerHasWon(player2));
    }
    
    @Test
    public void redDiagonalConnectionFromTopLeftToBottomRightIsNotAWinForRed() {
    	for(int i = 0; i < board.getBoardSize(); i++)
    		board.getTileAtPosition(i, board.getBoardSize() - i - 1).setColour(Tile.Colour.RED);
    	assertFalse(gameLogic.playerHasWon(player2));
    }
    
    @Test
    public void blueDiagonalConnectionFromTopLeftToBottomRightIsNotAWinForBlue() {
    	for(int i = 0; i < board.getBoardSize(); i++)
    		board.getTileAtPosition(i, board.getBoardSize() - i - 1).setColour(Tile.Colour.BLUE);
    	assertFalse(gameLogic.playerHasWon(player1));
    }
    
    @Test
    public void winCallbackCalledOnPlayerWin() {
    	PlayerCondition callback = mock(PlayerCondition.class);
    	GameLogic gSpy;
    	{
	    	GameLogic g = new GameLogic(board, player1, player2, callback);
	    	gSpy = spy(g);
    	}
    	when(gSpy.playerHasWon(player1)).thenReturn(true);
    	gSpy.nextTurn();
    	verify(callback, times(1)).met(player1);;
    }
}
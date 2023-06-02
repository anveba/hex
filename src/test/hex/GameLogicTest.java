package test.hex;

import main.hex.*;
import main.engine.*;
import main.hex.board.*;
import main.hex.player.PlayerCondition;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.*;
import org.mockito.*;

public class GameLogicTest {
    private TestPlayerClass player1, player2;
    private GameLogic gameLogic;
    private Board board;

    @Before
    public void setup() {
        board = new Board(11);
        player1 = spy(new TestPlayerClass(TileColour.PLAYER1));
        player2 = spy(new TestPlayerClass(TileColour.PLAYER2));
        gameLogic = new GameLogic(board, player1, player2);
        gameLogic.start();
    }
    
    @Test
    public void gettingBoardReturnsBoardGivenInConstructor() {
        assertEquals(board, gameLogic.getBoard());
    }

    // Tests for turns
    @Test
    public void createPlayers_firstPlayerTurn_player1() {
        assertEquals(player1, gameLogic.getCurrentTurnsPlayer());
    }
    
    @Test
    public void nextTurn_currentlyPlayer1_player2() {
    	player1.relayResponseAsMove(0, 0);
        gameLogic.update(mock(TimeRecord.class));
        assertEquals(player2, gameLogic.getCurrentTurnsPlayer());
    }

    @Test
    public void nextTurn_runTwiceCurrentlyPlayer1_player1() { // Checks that players are looped
    	player1.relayResponseAsMove(0, 0);
        gameLogic.update(mock(TimeRecord.class));
        player2.relayResponseAsMove(0, 1);
        gameLogic.update(mock(TimeRecord.class));
        assertEquals(player1, gameLogic.getCurrentTurnsPlayer());
    }

    // Tests for swap rule
    @Test
    public void swapRuleSwapsPlayersCorrectly() {
        gameLogic.setSwapRuleState(true);
    	player1.relayResponseAsMove(0, 0);
        gameLogic.update(mock(TimeRecord.class));
        player2.relayResponseAsMove(0, 0);
        gameLogic.update(mock(TimeRecord.class));
        assertEquals(gameLogic.getBoard().getTileAtPosition(0,0).getColour(), player2.getColour());
    }

    // Tests for win
    @Test
    public void whiteBoardIsNotAWinForBothPlayers() {
    	assertFalse(gameLogic.playerHasWon(player1));
    	assertFalse(gameLogic.playerHasWon(player2));
    }
    
    @Test
    public void redVerticalConnectionFromTopToBottomIsAWinForRed() {
    	for(int i = 0; i < board.size(); i++)
    		board.setTileAtPosition(new Tile(TileColour.PLAYER2), 0, i);
    	assertTrue(gameLogic.playerHasWon(player2));
    }
    
    @Test
    public void blueVerticalConnectionFromTopToBottomIsNotAWinForBlue() {
    	for(int i = 0; i < board.size(); i++)
    		board.setTileAtPosition(new Tile(TileColour.PLAYER1), 0, i);
    	assertFalse(gameLogic.playerHasWon(player1));
    }
    
    @Test
    public void blueHorizontalConnectionFromLeftToRightIsAWinForBlue() {
    	for(int i = 0; i < board.size(); i++)
    		board.setTileAtPosition(new Tile(TileColour.PLAYER1), i, 0);
    	assertTrue(gameLogic.playerHasWon(player1));
    }
    
    @Test
    public void redHorizontalConnectionFromLeftToRightIsNotAWinForRed() {
    	for(int i = 0; i < board.size(); i++)
    		board.setTileAtPosition(new Tile(TileColour.PLAYER2), i, 0);
    	assertFalse(gameLogic.playerHasWon(player2));
    }
    
    @Test
    public void boardWithCenterFilledWithRedAndWithRedConnectionFromTopToBottomIsAWinForRedAndNotAWinForBlue() {
    	for(int i = 1; i < board.size() - 1; i ++) {
    		for (int j = 1; j < board.size() - 1; j++) {
    			board.setTileAtPosition(new Tile(TileColour.PLAYER2), i, j);
    		}
    	}
    	
    	board.setTileAtPosition(new Tile(TileColour.PLAYER2), 1, 0);
    	board.setTileAtPosition(new Tile(TileColour.PLAYER2),
    			board.size() - 2, board.size() - 1);
    	
    	assertFalse(gameLogic.playerHasWon(player1));
    	assertTrue(gameLogic.playerHasWon(player2));
    }
    
    @Test
    public void blueDiagonalConnectionFromTopRightToBottomLeftIsAWinForBlue() {
    	for(int i = 0; i < board.size(); i++)
    		board.setTileAtPosition(new Tile(TileColour.PLAYER1), i, i);
    	assertTrue(gameLogic.playerHasWon(player1));
    }
    
    @Test
    public void redDiagonalConnectionFromTopRightToBottomLeftIsAWinForRed() {
    	for(int i = 0; i < board.size(); i++)
    		board.setTileAtPosition(new Tile(TileColour.PLAYER2), i, i);
    	assertTrue(gameLogic.playerHasWon(player2));
    }
    
    @Test
    public void redDiagonalConnectionFromTopLeftToBottomRightIsNotAWinForRed() {
    	for(int i = 0; i < board.size(); i++)
    		board.setTileAtPosition(new Tile(TileColour.PLAYER2), i, board.size() - i - 1);
    	assertFalse(gameLogic.playerHasWon(player2));
    }
    
    @Test
    public void blueDiagonalConnectionFromTopLeftToBottomRightIsAWinForBlue() {
    	for(int i = 0; i < board.size(); i++)
    		board.setTileAtPosition(new Tile(TileColour.PLAYER1), i, board.size() - i - 1);
    	assertFalse(gameLogic.playerHasWon(player1));
    }
    
    @Test
    public void winCallbackCalledOnPlayerWin() {
    	PlayerCondition callback = mock(PlayerCondition.class);
    	gameLogic.setPlayerWinCallback(callback);
    	
    	player1.relayResponseAsMove(0, 1);
		gameLogic.update(mock(TimeRecord.class));
    	for (int i = 1; i < board.size(); i++) {
    		player2.relayResponseAsMove(i, 2);
    		gameLogic.update(mock(TimeRecord.class));
    		player1.relayResponseAsMove(i, 1);
    		gameLogic.update(mock(TimeRecord.class));
    	}
    	
    	verify(callback, times(1)).met(player1);;
    }
    
    @Test(expected = HexException.class)
    public void exceptionIsThrownIfErrorIsInResponse() {
    	player1.relayResponseAsError(mock(Throwable.class));
		gameLogic.update(mock(TimeRecord.class));
    }
}
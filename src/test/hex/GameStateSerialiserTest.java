package test.hex;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import main.engine.graphics.Colour;
import main.hex.*;
import main.hex.board.*;
import main.hex.player.*;
import main.hex.serialisation.*;

public class GameStateSerialiserTest {
	
    @Test
    public void jsonSerialiseAndDeserialiseAreInversesOnASmallAndEmptyGame() {
        
    	final float player1Time = 20.1f;
    	final float player2Time = 24.1f;
    	GameLogic initialLogic = generateEmptyGame(3, player1Time, player2Time);
    	var initialCustom = generateGameCustomisation(false);
        
		var s = new GameStateSerialiser();
		String json1 = s.gameStateToJson(
				GameState.sessionToState(new GameSession(initialCustom, initialLogic)));
		var g = s.gameStateFromJson(json1);
		String json2 = s.gameStateToJson(g);
        
		assertEquals(json1, json2);
    }
    
    @Test
    public void gameStateLoadingAndSavingAreInversesOnASmallAndEmptyGame() {
        
    	final float player1Time = 20.1f;
    	final float player2Time = 24.1f;
    	GameLogic initialLogic = generateEmptyGame(3, player1Time, player2Time);
    	var initialCustom = generateGameCustomisation(false);
        
		var s = new GameStateSerialiser();
		String json1 = s.gameStateToJson(
				GameState.sessionToState(new GameSession(initialCustom, initialLogic)));
		var g = s.gameStateFromJson(json1);
		var loadedGame = g.stateToSession();
		String json2 = s.gameStateToJson(GameState.sessionToState(loadedGame));
        
		assertEquals(json1, json2);
    }
    
    private GameLogic generateEmptyGame(int boardSize, 
    		float player1Time, float player2Time) {
    	Board b = new Board(3);
        Player p1 = mockPlayer(TileColour.PLAYER1, player1Time, false);
        Player p2 = mockPlayer(TileColour.PLAYER2, player2Time, true);
        GameLogic g = new GameLogic(b, p1, p2, false);
        g.start();
        return g;
    }
    
    private Player mockPlayer(TileColour colour, double time, boolean mockAsAi) {
    	Player p = mockAsAi ? mock(AIPlayer.class) : mock(UserPlayer.class);
    	PlayerTimer timer = new PlayerTimer(time);
    	
    	when(p.getColour()).thenReturn(colour);
    	when(p.getTimer()).thenReturn(timer);
    	
    	return p;
    }
    
    private GameCustomisation generateGameCustomisation(boolean swapRule) {
    	
    	PlayerSkin p1Skin = new PlayerSkin(0, new Colour(1.0f, 0.4f, 0.2f, 1.0f));
    	PlayerSkin p2Skin = new PlayerSkin(0, new Colour(0.12f, 0.89f, 0.26f, 0.5f));
    	String p1Name = "Player1";
    	String p2Name = "Steve2";
    	int initialTimeLimit = 43;
    	
    	return new GameCustomisation(p1Name, p2Name, p1Skin, p2Skin, initialTimeLimit, swapRule);
    }
}

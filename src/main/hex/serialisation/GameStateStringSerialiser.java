package main.hex.serialisation;

import com.google.gson.Gson;

import main.hex.GameCustomisation;
import main.hex.GameLogic;
import main.hex.HexException;
import main.hex.board.*;
import main.hex.player.*;

/**
 * This class serialises and deserialises the board to/from a custom,
 * human-readable format. The format is represented by a string.
 * 
 * Example:
 * "001/002/010 2 3 56.2 47.3"
 * This is a board of size 3. The substring "001/002/010" represents the
 * tiles from top left to bottom right, where 0 is white/empty, 1 is a 
 * player 1 tile, and 2 is a player 2 tile. Forward slash is a row 
 * separator. The following "2" says that it is player 2's turn to move. 
 * The next "3" indicates that it is turn 3.
 * The following two real numbers represent the time remaining for 
 * player 1 and player 2, respectively.
 * 
 * @author Andreas
 *
 */
public class GameStateStringSerialiser {

	public GameStateStringSerialiser() {
		
	}
	
	public String serialise(GameLogic logic, GameCustomisation custom) {
		if (logic == null)
			throw new HexException("Null arguments given");
		
		Gson gson = new Gson();
		
		
		Board board = logic.getBoard();
		
		StringBuilder builder = new StringBuilder();
		
		
		//Serialise board
		for (int y = 0; y < board.size(); y++) {			
			for (int x = 0; x < board.size(); x++) {
				char c;
				TileColour col = board.getTileAtPosition(x, y).getColour();
				if (col == TileColour.PLAYER1)
					c = '1';
				else if (col == TileColour.PLAYER2)
					c = '2';
				else if (col == TileColour.WHITE)
					c = '0';
				else
					throw new HexException("Invalid colour at (" + x + ", " + y + ")");
				builder.append(c);
			}
			if (y != board.size() - 1)
				builder.append('/');
		}
		
		builder.append(' ');
		
		//Serialise player to move
		char playerToMove;
		if (logic.getCurrentTurnsPlayer().getColour() == TileColour.PLAYER1)
			playerToMove = '1';
		else if (logic.getCurrentTurnsPlayer().getColour() == TileColour.PLAYER2)
			playerToMove = '2';
		else
			throw new HexException("Invalid player to move");
		builder.append(playerToMove);
		
		builder.append(' ');
		
		//Serialise current turn number
		builder.append(Integer.toString(logic.getCurrentTurnNumber()));
		
		builder.append(' ');
		
		//Serialise player timers
		String player1Time = Float.toString((float)logic.getPlayer1().getTimer().getRemainingTime());
		String player2Time = Float.toString((float)logic.getPlayer2().getTimer().getRemainingTime());
		
		builder.append(player1Time);
		builder.append(' ');
		builder.append(player2Time);
		
		return builder.toString();
	}
	
	public GameLogic deserialise(String serialised) {
		if (serialised == null)
			throw new HexException("Invalid string (null)");
		
		String[] tokens = serialised.split(" ");
		if (tokens.length != 5)
			throw new HexException("Token count mismatch");
		
		Board board = parseBoard(tokens[0]);
		
		if (!(tokens[1].equals("1") || tokens[1].equals("2")))
			throw new HexException("Invalid starting player (" + tokens[1] + ")");
		final boolean player2Starts = tokens[1].equals("2");
		
		final int turnNumber = Integer.parseInt(tokens[2]);
		
		final float player1Time = Float.parseFloat(tokens[3]);
		final float player2Time = Float.parseFloat(tokens[4]);
		
		return null;
	}
	
	private Board parseBoard(String serialisedTiles) {
		
		String[] rows = serialisedTiles.split("/");
		final int size = rows.length;
		final Board board = new Board(size);
		
		for (int i = 0; i < size; i++) {
			String r = rows[i];
			if (r.length() != size)
				throw new HexException("Board is not square");
			
			for (int j = 0; j < size; j++) {
				final char c = r.charAt(j);
				TileColour col;
				if (c == '1')
					col = TileColour.PLAYER1;
				else if (c == '2')
					col = TileColour.PLAYER2;
				else if (c == '0')
					col = TileColour.WHITE;
				else
					throw new HexException("No colour represented by " + c);
				board.setTileAtPosition(new Tile(col), i, j);
			}
		}
		
		return board;
	}
}

package main.hex;

import main.engine.Point2;
import main.engine.input.Controls;
import main.engine.input.ControlsArgs;
import main.engine.input.ControlsListener;
import main.hex.Tile.Colour;

import java.util.ArrayDeque;

public class GameLogic {

    private Board board;
    private Player player1, player2;

    private ArrayDeque<Player> players = new ArrayDeque<>();
    
    private int currentTurn;
    private boolean gameIsOver;
    
    PlayerCondition playerWinCallback;

    public GameLogic(Board board, PlayerCondition playerWinCallback) {
        this(board, 
        		new Player(Tile.Colour.BLUE, true), 
        		new Player(Tile.Colour.RED, false), 
        		playerWinCallback);
    }
    
    public GameLogic(Board board) {
        this(board, new Player(Tile.Colour.BLUE, true), new Player(Tile.Colour.RED, false));
    }

    public GameLogic(Board board, Player player1, Player player2) {
        this(board, player1, player2, null);
    }
    
    public GameLogic(Board board, Player player1, Player player2, 
    		PlayerCondition playerWinCallback) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
        players.addLast(player1);
        players.addLast(player2);
        currentTurn = 0;
        gameIsOver = false;
        this.playerWinCallback = playerWinCallback;
    }
    
    public void setupControlsCallback(ControlsListener listener) {
    	Game.getInstance().getControlsListener()
    	.addOnPressCallback(Controls.LEFT_MOUSE, this::handleClick);
    }

    public Player getCurrentTurnsPlayer() {
        return players.peekFirst();
    }

    public void nextTurn() {
        for (Player p : players) {
	        if (playerHasWon(p)) {
	        	gameIsOver = true;
	        	if (playerWinCallback != null)
	        		playerWinCallback.met(p);
	        	return;
	        }
        }
        Player currentPlayer = players.removeFirst();
        players.addLast(currentPlayer);
        currentTurn++;
    }

    private void handleClick(ControlsArgs args) {
        Point2 tileIndex = board.screenToTile(Game.getInstance().getControlsListener().getCursorX(),
                Game.getInstance().getControlsListener().getCursorY());
        if (!board.isOutOfBounds(tileIndex.getX(), tileIndex.getY()))  	
        	placeColourOfCurrentPlayer(tileIndex.getX(), tileIndex.getY());
    }
    
    private void placeColourOfCurrentPlayer(int x, int y) {
    	if (board.isOutOfBounds(x, y))  
    		throw new HexException("Out of bounds");
    	if (gameIsOver)
    		return;

        Tile clickedTile = board.getTileAtPosition(x, y);
        if (clickedTile.getColour() == Tile.Colour.WHITE) {
            clickedTile.setColour(this.getCurrentTurnsPlayer().getPlayerColour());
            this.nextTurn();
        } else if (clickedTile.getColour() == player1.getPlayerColour()) {
        	swapPlayerColours();
        }
    }

    public void swapPlayerColours() {
        if (currentTurn == 1) {
            Tile.Colour tempCol = player1.getPlayerColour();
            player1.setPlayerColour(player2.getPlayerColour());
            player2.setPlayerColour(tempCol);
            this.nextTurn();
        }
    }
    
    public boolean playerHasWon(Player player) {
    	boolean[][] target = new boolean[board.size()][board.size()];
    	boolean[][] checked = new boolean[board.size()][board.size()];
    	for (int i = 0; i < board.size(); i++) {
    		target
    			[player.hasWonByVerticalConnection() ? i : board.size() - 1]
				[player.hasWonByVerticalConnection() ? board.size() - 1 : i] = true;
    	}
    	
    	for (int i = 0; i < board.size(); i++) {
    		if (colourConnectsToTarget(player.getPlayerColour(), 
    				player.hasWonByVerticalConnection() ? i : 0,
					player.hasWonByVerticalConnection() ? 0 : i,
					checked, target))
    			return true;
    	}
    	return false;
    }
    
    private boolean colourConnectsToTarget(
    		Colour col, int x, int y, 
    		boolean[][] checked, boolean[][] target) {
    	
    	if (checked[x][y])
    		return false;
    	checked[x][y] = true;
    	if (col != board.getTileAtPosition(x, y).getColour())
    		return false;
		if (target[x][y])
			return true;
    	for (int i = -1; i < 2; i++) {
    		for (int j = -1; j < 2; j++) {
    			if (i + j == 0 || board.isOutOfBounds(x + i, y + j))
    				continue;
    			if (colourConnectsToTarget(col, x + i, y + j, checked, target))
    				return true;
    		}
    	}
    	return false;
    }
}

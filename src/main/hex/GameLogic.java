package main.hex;

import main.engine.TimeRecord;
import main.hex.board.Board;
import main.hex.board.Tile;
import main.hex.board.TileColour;

import java.util.ArrayDeque;
import java.util.Deque;

public class GameLogic implements Updateable {

    private Board board;
    private Player player1, player2;
    private ConcurrentPlayerResponse playerResponse;

    private Deque<Player> players = new ArrayDeque<>();
    
    private int currentTurn;
    private boolean gameIsOver;
	private boolean swapRuleEnabled;
	private boolean coloursSwapped;
    
    private PlayerCondition playerWinCallback;
    
    public GameLogic(Board board, Player player1, Player player2) {
    	if (board == null || player1 == null || player2 == null)
    		throw new HexException("null was given");
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
        players.addLast(player2);
        players.addLast(player1);
        currentTurn = -1;
        gameIsOver = false;
		swapRuleEnabled = false;
		coloursSwapped = false;
    }
    
    public void start() {
    	if (currentTurn == -1)
    		nextTurn();
    	else
    		throw new HexException("Game already started");
    }
    
    public void setPlayerWinCallback(PlayerCondition callback) {
    	this.playerWinCallback = callback;
    }

    public Player getCurrentTurnsPlayer() {
    	if (currentTurn < 0)
    		throw new HexException("Game hasn't started yet.");
        return players.peekFirst();
    }
    
    /**
     * @return Returns true if the move is valid, otherwise false.
     */
    private boolean executeMoveOfCurrentPlayer(Move move) {
    	if (move == null)
    		throw new HexException("Move was null");
    	if (board.isOutOfBounds(move.getX(), move.getY()))  
    		return false;
    	if (gameIsOver)
    		throw new HexException("Could not execute move since the game is over");
    	

        Tile tile = board.getTileAtPosition(move.getX(), move.getY());

        if (tile.getColour() == TileColour.WHITE) {
        	board.setTileAtPosition(new Tile(players.peekFirst().getColour()), move.getX(), move.getY());
		} else if (tile.getColour() == player1.getColour() && currentTurn == 1 && swapRuleEnabled) {
			swapColours();
		} else {
			return false;
		}
        return true;
    }
    
    private void swapColours() {
    	players.stream().forEach((p) -> p.setColour(TileColour.opposite(p.getColour())));
    	coloursSwapped = true;
    }
    
    public boolean playerHasWon(Player player) {
    	if (currentTurn < 0)
    		throw new HexException("Game hasn't started yet.");
    	
    	boolean[][] target = new boolean[board.size()][board.size()];
    	boolean[][] checked = new boolean[board.size()][board.size()];
    	for (int i = 0; i < board.size(); i++) {
    		target
    			[player.winsByVerticalConnection() ? i : board.size() - 1]
				[player.winsByVerticalConnection() ? board.size() - 1 : i] = true;
    	}
    	
    	for (int i = 0; i < board.size(); i++) {
    		if (colourConnectsToTarget(player.getColour(), 
    				player.winsByVerticalConnection() ? i : 0,
					player.winsByVerticalConnection() ? 0 : i,
					checked, target))
    			return true;
    	}
    	return false;
    }
    
    private boolean colourConnectsToTarget(
    		TileColour col, int x, int y, 
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

    public Board getBoard() {
    	return board;
    }

	@Override
	public void update(TimeRecord elapsed) {
		pollPlayerResponse();
	}
	
	private void pollPlayerResponse() {
		if (!gameIsOver && playerResponse != null) {
			Throwable error = playerResponse.getError();
			if (error != null)
				throw new HexException(error);
			Move m = playerResponse.getMove();
			if (m != null) {
				players.peekFirst().onTurnReceival();
				if (executeMoveOfCurrentPlayer(m)) {
					nextTurn();
				}
				else {
					playerResponse = new ConcurrentPlayerResponse();
					players.peekFirst().correctInvalidTurn(board, playerResponse);
				}
			}
		}
	}
	
    private void nextTurn() {
    	if (currentTurn >= 0) {
	    	for (Player p : players) {
		        if (playerHasWon(p)) {
		        	handleWinFor(p);
		        	return;
		        }
	        }
    	}
        Player currentPlayer = players.removeFirst();
        players.addLast(currentPlayer);
        currentTurn++;
        currentPlayer.onEndOfTurn();
        
        playerResponse = new ConcurrentPlayerResponse();
        players.peekFirst().processTurn(board, playerResponse);
    }

	private void handleWinFor(Player p) {
		gameIsOver = true;
		if (playerWinCallback != null)
			playerWinCallback.met(p);
	}

	public void setSwapRuleState(boolean swapRuleState) {
		this.swapRuleEnabled = swapRuleState;
	}
	
	public boolean coloursSwapped() {
		return coloursSwapped;
	}
}

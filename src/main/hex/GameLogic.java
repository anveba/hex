package main.hex;

import main.engine.Point2;
import main.engine.input.Controls;
import main.engine.input.ControlsArgs;
import main.engine.input.ControlsListener;

import java.util.ArrayDeque;

public class GameLogic {

    private Board board;
    private Player player1, player2;

    private ArrayDeque<Player> players = new ArrayDeque<>();

    public GameLogic(Board board) {
        this(board, new Player(Tile.Colour.BLUE), new Player(Tile.Colour.RED));
    }

    public GameLogic(Board board, Player player1, Player player2) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
        players.addLast(player1);
        players.addLast(player2);
    }
    
    public void setupControlsCallback(ControlsListener listener) {
    	Game.getInstance().getControlsListener()
    	.addOnPressCallback(Controls.LEFT_MOUSE, this::handleClick);
    }

    public Player getPlayerTurn() {
        return players.peekFirst();
    }

    public void nextPlayer() {
        Player currentPlayer = players.removeFirst();
        players.addLast(currentPlayer);
    }

    private void handleClick(ControlsArgs args) {

        Point2 tileIndex = board.screenToTile(Game.getInstance().getControlsListener().getCursorX(),
                Game.getInstance().getControlsListener().getCursorY());

        if (!board.isOutOfBounds(tileIndex.getX(), tileIndex.getY())) {
            Tile clickedTile = board.getTileAtPosition(tileIndex.getX(), tileIndex.getY());

            if (clickedTile.getColour() == Tile.Colour.WHITE) {
                clickedTile.setColour(this.getPlayerTurn().getPlayerColor());
                this.nextPlayer();
            }
        }
    }
}

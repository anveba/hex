package hex;

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

    public Player getPlayerTurn() {
        return players.peekFirst();
    }

    public void nextPlayer() {
        Player currentPlayer = players.removeFirst();
        players.addLast(currentPlayer);
    }
}

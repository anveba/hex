package main.hex.ui;

import main.engine.ResourceManager;
import main.engine.font.BitmapFont;
import main.engine.graphics.Renderer2D;
import main.engine.graphics.Texture;
import main.engine.ui.*;
import main.hex.Board;
import main.hex.GameLogic;

public class GameFrame extends Frame {

    // Fonts:
    private BitmapFont FONT_FREDOKA_ONE = ResourceManager.getInstance().loadFont("fonts/fredoka-one.one-regular.ttf");

    // Textures:
    private Texture TEXTURE_LARGE_MENU_GREY = ResourceManager.getInstance().loadTexture("textures/gui/Icons/Icon_Large_Menu_Grey.png");
    private Texture TEXTURE_SMALL_WHITEOUTLINE_RETURN = ResourceManager.getInstance().loadTexture("textures/gui/Icons/Icon_Small_WhiteOutline_Return.png");
    private Texture TEXTURE_BACKGROUND_SQUARE = ResourceManager.getInstance().loadTexture("textures/gui/ButtonsIcons/IconButton_Large_Background_Square.png");
    private Texture TEXTURE_BLUE_TILE = ResourceManager.getInstance().loadTexture("textures/board/blue_tile.png");
    private Texture TEXTURE_RED_TILE = ResourceManager.getInstance().loadTexture("textures/board/red_tile.png");

    public static final float tileSizeX = 0.08f;
    public static final float tileSizeY = tileSizeX * 1.1547005f;
    private Board board;
    private GameLogic gameLogic;

    public GameFrame(Board board) {

        this.board = board;

        //Main menu extends Frame, so it has a UI element as a root
        UIGroup root = new UIGroup(0.0f, 0.0f);
        setRoot(root);

        initializeGameFrame(root);
    }

    @Override
    public void draw(Renderer2D renderer) {
        if (board != null)
            board.draw(renderer);
        super.draw(renderer);
    }

    private void initializeGameFrame(UIGroup root) {
        UIGroup gameFrameView = new UIGroup(0.0f, 0.0f);
        root.addChild(gameFrameView);

        gameFrameView.addChild(createMenuView());
        gameFrameView.addChild(createUndoView());
        gameFrameView.addChild(createPlayerViews());
    }

    private UIGroup createMenuView() {
        UIGroup menuView = new UIGroup(0.0f, 0.0f);
        Image menuSymbol = new Image(0.9f, 0.9f, 0.1f, 0.1f, TEXTURE_LARGE_MENU_GREY,
                0, 0, TEXTURE_LARGE_MENU_GREY.width(), TEXTURE_LARGE_MENU_GREY.height());
        menuView.addChild(menuSymbol);

        return menuView;
    }

    private UIGroup createUndoView() {
        UIGroup undoView = new UIGroup(0.0f, 0.0f);
        //TODO: Find a more suiting icon for return, since this is more like a reload
        Image undoSymbol = new Image(0.0f, -0.91f, 0.1f, 0.1f,
                TEXTURE_SMALL_WHITEOUTLINE_RETURN, 0, 0,
                TEXTURE_SMALL_WHITEOUTLINE_RETURN.width(), TEXTURE_SMALL_WHITEOUTLINE_RETURN.height());
        undoView.addChild(undoSymbol);

        return undoView;
    }

    private UIGroup createPlayerViews() {
        UIGroup playerViews = new UIGroup(0.0f, 0.0f);

        playerViews.addChild(createPlayer1View());
        playerViews.addChild(createPlayer2View());

        return playerViews;
    }

    private UIGroup createPlayer1View() {
        UIGroup player1UIGroup = new UIGroup(0.0f, 0.0f);

        player1UIGroup.addChild(createPlayerViewBackground(-0.715f, -0.8f));
        player1UIGroup.addChild(createTileView(-0.88f, -0.705f, TEXTURE_BLUE_TILE)); //TODO: has to be updated to be dynamic for the skin when another skin is chosen
        player1UIGroup.addChild(createPlayerNameView(-0.665f, -0.705f, "Player 1")); //TODO: has to be updated to be dynamic for the name when another name is chosen
        player1UIGroup.addChild(createTimerView(-0.72f, -0.85f, "0:32"));

        return player1UIGroup;
    }

    private UIGroup createPlayer2View() {
        UIGroup player2UIGroup = new UIGroup(0.0f, 0.0f);

        player2UIGroup.addChild(createPlayerViewBackground(0.72f, -0.8f));
        player2UIGroup.addChild(createTileView(0.555f, -0.705f, TEXTURE_RED_TILE)); //TODO: has to be updated to be dynamic for the skin when another skin is chosen
        player2UIGroup.addChild(createPlayerNameView(0.77f, -0.705f, "Player 2")); //TODO: has to be updated to be dynamic for the name when another name is chosen
        player2UIGroup.addChild(createTimerView(0.72f, -0.85f, "1:43"));

        return player2UIGroup;
    }

    private UIGroup createPlayerViewBackground(float xPos, float yPos) {
        UIGroup playerViewBackgroundUIGroup = new UIGroup(0.0f, 0.0f);
        Image playerViewBackground = new Image(xPos, yPos, 0.5f, 0.35f, TEXTURE_BACKGROUND_SQUARE,
                0, 0, TEXTURE_BACKGROUND_SQUARE.width(), TEXTURE_BACKGROUND_SQUARE.height());
        playerViewBackgroundUIGroup.addChild(playerViewBackground);

        return playerViewBackgroundUIGroup;
    }

    private UIGroup createTileView(float xPos, float yPos, Texture tileSkin) {
        UIGroup tileViewUIGroup = new UIGroup(0.0f, 0.0f);
        Image tileView = new Image(xPos, yPos, tileSizeX, tileSizeY, tileSkin,
                0, 0, tileSkin.width(), tileSkin.height());
        tileViewUIGroup.addChild(tileView);

        return tileViewUIGroup;
    }

    private UIGroup createPlayerNameView(float xPos, float yPos, String playerName) {
        UIGroup playerNameViewUIGroup = new UIGroup(0.0f, 0.0f);
        Text playerNameText = new Text(xPos, yPos, FONT_FREDOKA_ONE, playerName, 0.04f);
        playerNameViewUIGroup.addChild(playerNameText);

        return playerNameViewUIGroup;
    }

    // TODO: This takes just a placeholder text
    private UIGroup createTimerView(float xPos, float yPos, String timer) {
        UIGroup timerViewUIGroup = new UIGroup(0.0f, 0.0f);
        Text timerText = new Text(xPos, yPos, FONT_FREDOKA_ONE, timer, 0.08f);
        timerViewUIGroup.addChild(timerText);

        return timerViewUIGroup;
    }
}

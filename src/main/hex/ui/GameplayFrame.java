package main.hex.ui;

import main.engine.ResourceManager;
import main.engine.TimeRecord;
import main.engine.font.BitmapFont;
import main.engine.graphics.Texture;
import main.engine.ui.*;
import main.hex.Game;
import main.hex.GameCustomisation;
import main.hex.GameLogic;
import main.hex.player.PlayerSkin;
import main.hex.resources.TextureLibrary;
import main.hex.scene.MainMenuScene;
import main.hex.scene.SceneDirector;
import main.hex.scene.TitleScene;
import main.hex.serialisation.GameSession;
import main.hex.serialisation.HexFileSystem;

public class GameplayFrame extends Frame {

    // Fonts:
    private BitmapFont FONT_FREDOKA_ONE = ResourceManager.getInstance().loadFont("fonts/fredoka-one.one-regular.ttf");

    private final String PAUSE_MENU_TITLE = "PAUSED";
    private final String PAUSE_MENU_RESUME_BTN = "Resume";
    private final String PAUSE_MENU_OPTIONS_BTN = "Options";
    private final String PAUSE_MENU_SAVE_GAME_BTN = "Save game";
    private final String PAUSE_MENU_MAIN_MENU_BTN = "Main menu";
    private final String PAUSE_MENU_EXIT_BTN = "Exit Game";

    private static final float tileSizeX = 0.08f;
    private static final float tileSizeY = tileSizeX * 1.1547005f;
    private GameCustomisation gameCustomisation;
    private GameLogic gameLogic;
    private UIGroup pauseMenuUIGroup;

    public GameplayFrame(GameCustomisation gameCustomisation, GameLogic gameLogic) {

        this.gameCustomisation = gameCustomisation;
        this.gameLogic = gameLogic;
        UIGroup root = new UIGroup(0.0f, 0.0f);
        setRoot(root);

        initializeGameFrame(root);
    }

    private void initializeGameFrame(UIGroup root) {
        UIGroup gameFrameView = new UIGroup(0.0f, 0.0f);
        root.addChild(gameFrameView);

        gameFrameView.addChild(createMenuView());
        gameFrameView.addChild(createUndoView());
        gameFrameView.addChild(createPlayerViews());

        //Pause menu (added last, so it's on top of everything else)
        pauseMenuUIGroup = createPauseMenu();
        pauseMenuUIGroup.disable(); //Initially hidden
        root.addChild(pauseMenuUIGroup);

    }

    private UIGroup createMenuView() {
        UIGroup menuView = new UIGroup(0.9f, 0.9f);

        ButtonCallback burgerMenuClicked = (args) -> {
            openPauseMenuBtnClicked();
        };
        RectButton burgerMenu = new RectButton(
                0.0f,
                0.0f,
                0.1f,
                0.1f,
                TextureLibrary.LARGE_MENU_GREY.getTexture(),
                FONT_FREDOKA_ONE,
                "",
                0.1f,
                burgerMenuClicked,
                null,
                null
        );
        menuView.addChild(burgerMenu);


        return menuView;
    }

    private UIGroup createUndoView() {
        UIGroup undoView = new UIGroup(0.0f, 0.0f);
        Image undoSymbol = new Image(0.0f, -0.91f, 0.12f, 0.12f, TextureLibrary.SMALL_UNDO_GREY.getTexture());
        undoView.addChild(undoSymbol);

        return undoView;
    }

    private UIGroup createPlayerViews() {
        UIGroup playerViews = new UIGroup(0.0f, 0.0f);

        playerViews.addChild(createPlayer1View());
        playerViews.addChild(createPlayer2View());

        return playerViews;
    }

    private Text player1TimerText, player2TimerText;

    private UIGroup createPlayer1View() {
        UIGroup player1UIGroup = new UIGroup(0.0f, 0.0f);

        player1UIGroup.addChild(createPlayerViewBackground(-0.715f, -0.8f));
        player1UIGroup.addChild(createTileView(-0.88f, -0.705f, gameCustomisation.getPlayer1Skin()));
        player1UIGroup.addChild(createPlayerNameView(-0.665f, -0.705f, gameCustomisation.getPlayer1Name()));
        player1TimerText = new Text(-0.72f, -0.85f, FONT_FREDOKA_ONE, gameLogic.getPlayer1().getTimer().getFormattedTime(), 0.08f);
        player1UIGroup.addChild(player1TimerText);

        return player1UIGroup;
    }

    private UIGroup createPlayer2View() {
        UIGroup player2UIGroup = new UIGroup(0.0f, 0.0f);

        player2UIGroup.addChild(createPlayerViewBackground(0.72f, -0.8f));
        player2UIGroup.addChild(createTileView(0.555f, -0.705f, gameCustomisation.getPlayer2Skin()));
        player2UIGroup.addChild(createPlayerNameView(0.77f, -0.705f, gameCustomisation.getPlayer2Name()));
        player2TimerText = new Text(0.72f, -0.85f, FONT_FREDOKA_ONE, gameLogic.getPlayer2().getTimer().getFormattedTime(), 0.08f);
        player2UIGroup.addChild(player2TimerText);

        return player2UIGroup;
    }

    private UIGroup createPlayerViewBackground(float xPos, float yPos) {
        UIGroup playerViewBackgroundUIGroup = new UIGroup(0.0f, 0.0f);
        Image playerViewBackground = new Image(xPos, yPos, 0.5f, 0.35f,
                TextureLibrary.BACKGROUND_SQUARE.getTexture());
        playerViewBackgroundUIGroup.addChild(playerViewBackground);

        return playerViewBackgroundUIGroup;
    }

    private UIGroup createTileView(float xPos, float yPos, PlayerSkin playerSkin) {
        UIGroup tileViewUIGroup = new UIGroup(0.0f, 0.0f);
        Image tileView = new Image(xPos, yPos, tileSizeX, tileSizeY, playerSkin.getTexture(),
                playerSkin.getTint());
        tileViewUIGroup.addChild(tileView);

        return tileViewUIGroup;
    }

    private UIGroup createPlayerNameView(float xPos, float yPos, String playerName) {
        UIGroup playerNameViewUIGroup = new UIGroup(0.0f, 0.0f);
        Text playerNameText = new Text(xPos, yPos, FONT_FREDOKA_ONE, playerName, 0.04f);
        playerNameViewUIGroup.addChild(playerNameText);

        return playerNameViewUIGroup;
    }

    private UIGroup createPauseMenu() {
        UIGroup pauseMenu = new UIGroup(0.0f, 0.1f);

        Image pauseMenuBackground = new Image(0.0f, 0.0f, 1.1f, 1.3f, TextureLibrary.BOX_WHITE_OUTLINE_ROUNDED.getTexture());
        pauseMenu.addChild(pauseMenuBackground);

        //Creating banner
        UIGroup pauseMenuBanner = new UIGroup(0.0f, 0.61f);
        Image pauseMenuBannerBackground = new Image(0.0f, 0.0f, 0.7f, 0.2f, TextureLibrary.BUTTON_TEXT_LARGE_SQUARE.getTexture());
        pauseMenuBanner.addChild(pauseMenuBannerBackground);
        Text bannerText = new Text(0.0f, 0.03f, FONT_FREDOKA_ONE, PAUSE_MENU_TITLE, 0.15f);
        pauseMenuBanner.addChild(bannerText);
        pauseMenu.addChild(pauseMenuBanner);

        //Creating button callbacks:
        ButtonCallback resumeToGameClicked = (args) -> resumeToGameBtnClicked();
        ButtonCallback mainMenuClicked = (args) -> mainMenuBtnClicked();
        ButtonCallback exitGameClicked = (args) -> exitGameBtnClicked();
        ButtonCallback optionsClicked = (args) -> optionsBtnClicked();
        ButtonCallback saveGameClicked = (args) -> saveGaneBtnClicked();

        //Creating red "exit pause" button
        pauseMenu.addChild(new RectButton(
                        0.50f,
                        0.61f,
                        0.15f,
                        0.15f,
                        TextureLibrary.PREMADE_BUTTONS_X_WHITE_OUTLINE.getTexture(),
                        FONT_FREDOKA_ONE,
                        "",
                        0.0f,
                        resumeToGameClicked, null, null
        ));

        pauseMenu.addChild(createPauseMenuButton(0, TextureLibrary.BUTTON_TEXT_LARGE_ORANGE_ROUND.getTexture(), PAUSE_MENU_RESUME_BTN, resumeToGameClicked));
        pauseMenu.addChild(createPauseMenuButton(1, TextureLibrary.BUTTON_TEXT_LARGE_ORANGE_ROUND.getTexture(), PAUSE_MENU_OPTIONS_BTN, optionsClicked));
        pauseMenu.addChild(createPauseMenuButton(2, TextureLibrary.BUTTON_TEXT_LARGE_ORANGE_ROUND.getTexture(), PAUSE_MENU_SAVE_GAME_BTN, saveGameClicked));
        pauseMenu.addChild(createPauseMenuButton(3, TextureLibrary.BUTTON_TEXT_LARGE_ORANGE_ROUND.getTexture(), PAUSE_MENU_MAIN_MENU_BTN, mainMenuClicked));
        pauseMenu.addChild(createPauseMenuButton(4, TextureLibrary.BUTTON_TEXT_LARGE_RED_ROUND.getTexture(), PAUSE_MENU_EXIT_BTN, exitGameClicked));

        return pauseMenu;
    }

    public RectButton createPauseMenuButton(int n, Texture texture, String string, ButtonCallback clickCallback) {
        return new RectButton(0.0f, 0.37f - 0.21f * n, 0.7f, 0.18f, texture, FONT_FREDOKA_ONE, string, 0.12f, clickCallback, null, null);
    }

    @Override
    public void update(TimeRecord elapsed) {
        player1TimerText.setText(gameLogic.getPlayer1().getTimer().getFormattedTime());
        player2TimerText.setText(gameLogic.getPlayer2().getTimer().getFormattedTime());
    }

    private void openPauseMenuBtnClicked() {
        pauseMenuUIGroup.enable();
        SceneDirector.currentScene().pauseUpdates();
    }
    private void resumeToGameBtnClicked() {
        pauseMenuUIGroup.disable();
        SceneDirector.currentScene().resumeUpdates();
    }
    private void optionsBtnClicked() {
        System.out.println("Options button clicked");
    }
    private void mainMenuBtnClicked() {
        SceneDirector.changeScene(new MainMenuScene());
    }

    private void saveGaneBtnClicked() {
    	HexFileSystem.getInstance().saveGame(new GameSession(gameCustomisation, gameLogic));
    }

    private void exitGameBtnClicked() {
        Game.getInstance().closeWindow();
    }
}
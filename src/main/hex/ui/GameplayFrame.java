package main.hex.ui;

import main.engine.TimeRecord;
import main.engine.font.BitmapFont;
import main.engine.graphics.Colour;
import main.engine.graphics.Texture;
import main.engine.io.ResourceManager;
import main.engine.ui.*;
import main.engine.ui.animation.*;
import main.engine.ui.animation.easing.*;
import main.engine.ui.callback.ButtonCallback;
import main.hex.Game;
import main.hex.HexException;
import main.hex.board.Board;
import main.hex.logic.GameCustomisation;
import main.hex.logic.GameLogic;
import main.hex.player.Player;
import main.hex.player.PlayerSkin;
import main.hex.resources.TextureLibrary;
import main.hex.scene.GameplayScene;
import main.hex.scene.SceneDirector;
import main.hex.scene.GameSetupScene;
import main.hex.serialisation.GameSession;
import main.hex.serialisation.HexFileSystem;

/**
 * The gameplay frame is the frame that is shown during gameplay.
 * It contains the board, the player's HUD (where player name, skin and timer is shown), the pause menu, and the win menu.
 * The board is drawn before the frame (handled by the scene), and is therefore not a part of the frame.
 *
 * @Author Oliver Grønborg Christensen - s204479
 * @Author Oliver Siggard - s204450
 */


public class GameplayFrame extends Frame {

    // Fonts:
    private BitmapFont FONT_FREDOKA_ONE = ResourceManager.getInstance().loadFont("fonts/fredoka-one.one-regular.ttf");

    private final String PAUSE_MENU_TITLE = "PAUSED";
    private final String PAUSE_MENU_RESUME_BTN = "Resume";
    private final String PAUSE_MENU_OPTIONS_BTN = "Options";
    private final String PAUSE_MENU_SAVE_GAME_BTN = "Save Game";
    private final String PAUSE_MENU_MAIN_MENU_BTN = "Main Menu";
    private final String PAUSE_MENU_EXIT_BTN = "Exit Game";

    private final String WIN_MENU_TITLE = "GAME ENDED";
    private final String WIN_MENU_TEXT = "{} won the game!";
    private final String WIN_MENU_RESTART_BTN = "Restart Game";
    private final String WIN_MENU_MAIN_MENU_BTN = "Main Menu";

    
    private final float pauseMenuAnimationTime = 0.8f;

    private static final float tileSizeX = 0.08f;
    private static final float tileSizeY = tileSizeX * 1.1547005f;
    private GameCustomisation gameCustomisation;
    private GameLogic gameLogic;
    private UIGroup undoBtnUIGroup;
    private UIGroup pauseMenuBtnUIGroup;
    private UIGroup pauseMenuUIGroup;
    private UIGroup winMenuUIGroup;
    private RectButton undoBtn;
    private Image blackOutImage;
    private Text toastText;

    private Animator winMenuAnimator;
    private Animator pauseMenuAnimator;
    private Text winMenuTitleText;

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

        //Win menu (added almost last, so it's on top of everything else but the pause menu)


        //Pause menu (added last, so it's on top of everything else)
        pauseMenuUIGroup = createPauseMenu();
        pauseMenuUIGroup.hide(); //Initially hidden
        root.addChild(pauseMenuUIGroup);

        //Win menu (added last, so it's on top of everything else)
        winMenuUIGroup = createWinMenu();
        winMenuUIGroup.hide(); //Initially hidden
        root.addChild(winMenuUIGroup);

        toastText = new Text(0.0f, 0.0f, FONT_FREDOKA_ONE, "", 0.1f);
        toastText.hide();
        root.addChild(toastText);
        
        blackOutImage = new Image(0.0f, 0.0f, 50.0f, 2.0f,
        		TextureLibrary.WHITE_PX.getTexture(), Colour.DarkGrey);
        blackOutImage.hide();
        root.addChild(blackOutImage);
        
    }

    public void fadeIn(float time) {
    	if (time <= 0.0f)
    		throw new HexException("Time was not positive");
    	blackOutImage.show();
    	AnimationSequence anim = new AnimationSequence(
    			new Wait(0.4f),
    			new Ease(blackOutImage, new CubicInOut(),
    					0.0f, 0.0f, 0.0f, 2.0f,
    					time),
    			new Wait(time),
    			new Hide(blackOutImage)
    			);
    	addAnimator(new Animator(anim));
    }

	private void fadeOut(float time, Runnable onEnd) {
		if (time <= 0.0f)
    		throw new HexException("Time was not positive");
    	blackOutImage.show();
    	AnimationSequence anim = new AnimationSequence(
    			new Ease(blackOutImage, new CubicInOut(),
    					0.0f, 2.0f, 0.0f, 0.0f,
    					time),
    			new Wait(time + 0.3f)
    			);
    	anim.setOnEndAction(onEnd);
    	addAnimator(new Animator(anim));
	}

    /**
     *  Author: Oliver Siggaard
     *  All methods below.
     */
    private UIGroup createMenuView() {
        pauseMenuBtnUIGroup = new UIGroup(0.0f, 0.0f);
        RectButton pauseMenuButton = new RectButton(
                0.9f,
                0.9f,
                0.14f,
                0.14f,
                TextureLibrary.MENU_BUTTON.getTexture(),
                FONT_FREDOKA_ONE,
                "",
                0.1f,
                args -> openPauseMenuBtnClicked(),
                null,
                null
        );
        pauseMenuBtnUIGroup.addChild(pauseMenuButton);

        return pauseMenuBtnUIGroup;
    }

    private UIGroup createUndoView() {
        undoBtnUIGroup = new UIGroup(0.0f, 0.0f);
        undoBtn = new RectButton(0.0f, -0.91f, 0.12f, 0.12f,
        		TextureLibrary.SMALL_UNDO_GREY.getTexture(), FONT_FREDOKA_ONE, "", 
        		0, null, null, null);
        undoBtn.setClickCallback(args -> undoBtnClicked());
        undoBtnUIGroup.addChild(undoBtn);

        return undoBtnUIGroup;
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

        player2UIGroup.addChild(createPlayerViewBackground(0.715f, -0.8f));
        player2UIGroup.addChild(createTileView(0.555f, -0.705f, gameCustomisation.getPlayer2Skin()));
        player2UIGroup.addChild(createPlayerNameView(0.77f, -0.705f, gameCustomisation.getPlayer2Name()));
        player2TimerText = new Text(0.72f, -0.85f, FONT_FREDOKA_ONE, gameLogic.getPlayer2().getTimer().getFormattedTime(), 0.08f);
        player2UIGroup.addChild(player2TimerText);

        return player2UIGroup;
    }

    private Image createPlayerViewBackground(float xPos, float yPos) {
        Image playerViewBackground = new Image(xPos, yPos, 0.5f, 0.35f,
                TextureLibrary.BACKGROUND_SQUARE.getTexture());

        return playerViewBackground;
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

    @Override
    public void update(TimeRecord elapsed) {
        player1TimerText.setText(gameLogic.getPlayer1().getTimer().getFormattedTime());
        player2TimerText.setText(gameLogic.getPlayer2().getTimer().getFormattedTime());
        if (gameLogic.historyLength() < 1)
        	undoBtn.disable();
        else
        	undoBtn.enable();
    }


    /**
     *  Author: Oliver Grønborg Christensen
     *  All methods below.
     */
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
        ButtonCallback saveGameClicked = (args) -> saveGameBtnClicked();

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

    private UIGroup createWinMenu() {

        UIGroup winMenu = new UIGroup(0.0f, 0.0f);

        //Creating banner
        float bannerYPos = 0.85f;
        UIGroup winMenuBanner = new UIGroup(0.0f, bannerYPos);
        Image winMenuBannerBackground = new Image(0.0f, 0.0f, 0.9f, 0.23f, TextureLibrary.BUTTON_LARGE_ORANGE_SQUARE.getTexture());
        winMenuBanner.addChild(winMenuBannerBackground);
        Text bannerText = new Text(0.0f, 0.03f, FONT_FREDOKA_ONE, WIN_MENU_TITLE, 0.13f);
        winMenuBanner.addChild(bannerText);
        winMenu.addChild(winMenuBanner);

        //Win Text
        float textYPos = 0.68f;
        winMenuTitleText = new Text(0.0f, textYPos, FONT_FREDOKA_ONE, "A Player won the Game!", 0.13f);
        winMenu.addChild(winMenuTitleText);


        //Restart button box
        UIGroup winMenuBtnBox = new UIGroup(0.0f, -0.80f);
        winMenu.addChild(winMenuBtnBox);

        //Creating button callbacks:
        ButtonCallback mainMenuClicked = (args) -> mainMenuBtnClicked();
        ButtonCallback restartGameClicked = (args) -> restartGameBtnClicked();

        float btnYCenter = 0.09f;
        RectButton mainMenuBtn = new RectButton(
                0.0f,
                btnYCenter - 2.f,
                0.65f,
                0.16f,
                TextureLibrary.BUTTON_LARGE_ORANGE_SQUARE.getTexture(),
                FONT_FREDOKA_ONE,
                WIN_MENU_MAIN_MENU_BTN,
                0.1f,
                mainMenuClicked,
                null,
                null
        );
        winMenuBtnBox.addChild(mainMenuBtn);

        RectButton restartGameBtn = new RectButton(
                0.0f,
                -btnYCenter - 2.f,
                0.65f,
                0.16f,
                TextureLibrary.BUTTON_LARGE_GREEN_SQUARE.getTexture(),
                FONT_FREDOKA_ONE,
                WIN_MENU_RESTART_BTN,
                0.1f,
                restartGameClicked,
                null,
                null
        );

        winMenuBtnBox.addChild(restartGameBtn);


        //Animations
        AnimationSequence animationSequence = new AnimationSequence();
        float animationDelay = 0.0f;

        animationSequence.append(new Ease(mainMenuBtn, new CubicInOut(), 0.0f, btnYCenter - 0.5f, 0.0f, btnYCenter, 1.1f), new Wait(animationDelay));
        animationSequence.append(new Ease(restartGameBtn, new CubicInOut(), 0.0f, -btnYCenter - 0.5f, 0.0f, -btnYCenter, 1.1f), new Wait(animationDelay));
        animationSequence.append(new Ease(winMenuTitleText, new CubicInOut(), 0.0f, textYPos + 0.5f, 0.0f, textYPos, 1.1f), new Wait(animationDelay));
        animationSequence.append(new Ease(winMenuBanner, new CubicInOut(), 0.0f, bannerYPos + 0.5f, 0.0f, bannerYPos, 1.1f), new Wait(animationDelay));

        winMenuAnimator = new Animator(animationSequence);

        return winMenu;
    }

    public RectButton createPauseMenuButton(int n, Texture texture, String string, ButtonCallback clickCallback) {
        return new RectButton(0.0f, 0.37f - 0.21f * n, 0.7f, 0.18f, texture, FONT_FREDOKA_ONE, string, 0.12f, clickCallback, null, null);
    }

    private void openPauseMenuBtnClicked() {
        if (SceneDirector.isPaused()) {
            resumeToGameBtnClicked();
        } else {
            removeAnimator(pauseMenuAnimator);
            pauseMenuAnimator = new Animator(
                    new Ease(pauseMenuUIGroup, new CubicOut(),
                            5.0f, 0.0f, 0.0f, 0.0f,
                            pauseMenuAnimationTime));
            addAnimator(pauseMenuAnimator);
            pauseMenuUIGroup.show();
            SceneDirector.pause();
        }
    }

    private void resumeToGameBtnClicked() {
    	removeAnimator(pauseMenuAnimator);
    	pauseMenuAnimator = new Animator(
    			new AnimationSequence(
    	    			new Ease(pauseMenuUIGroup, new CubicIn(),
    	    					0.0f, 0.0f, -5.0f, 0.0f, 
    	    					pauseMenuAnimationTime),
    	    			new Wait(pauseMenuAnimationTime),
    	    			new Hide(pauseMenuUIGroup)
    			));
    	addAnimator(pauseMenuAnimator);
        SceneDirector.resume();
    }
    private void optionsBtnClicked() {
        FrameStack.getInstance().push(new OptionsFrame());
    }
    private void mainMenuBtnClicked() {
    	fadeOut(1.0f, () -> {
	    	gameLogic.stop();
	        SceneDirector.changeScene(new GameSetupScene());
	        SceneDirector.resume();
    	});
    }
    private void restartGameBtnClicked() {
        boolean swapRule = gameCustomisation.getSwapRule();
        Board board = new Board(gameLogic.getBoard().size());
        Player player1 = gameLogic.getPlayer1();
        player1.getTimer().setTime(gameCustomisation.getInitialTimeLimit());
        Player player2 = gameLogic.getPlayer2();
        player2.getTimer().setTime(gameCustomisation.getInitialTimeLimit());
        SceneDirector.changeScene(
                new GameplayScene(
                        new GameLogic(
                                board,
                                player1,
                                player2,
                                swapRule
                        ),
                        gameCustomisation)
        );
        SceneDirector.resume();
    }

    private void saveGameBtnClicked() {
    	toast(3.0f, "Game has been saved");
    	HexFileSystem.getInstance().saveGame(new GameSession(gameCustomisation, gameLogic));
    }

    private void toast(float time, String text) {
    	toastText.show();
    	toastText.setText(text);
    	final float start = -1.2f, end = -0.7f;
    	AnimationSequence anim = new AnimationSequence(
    		new Ease(toastText, new CubicInOut(),
    				0.0f, start, 0.0f, end,
    				1.0f
				),
    		new Wait(time),
    		new Ease(toastText, new CubicInOut(),
    				0.0f, end, 0.0f, start,
    				1.0f
				)
    	);
    	anim.setOnEndAction(() -> { toastText.hide(); });
    	addAnimator(new Animator(anim));
    }

    private void exitGameBtnClicked() {
    	fadeOut(1.0f, () -> Game.getInstance().closeWindow());
    }
    
    private void undoBtnClicked() {
    	if (gameLogic.historyLength() > 0) {
    		undoBtn.disable();
    		gameLogic.undoLast();
    	}
    }

    public void onPlayerWin(Player player) {
        SceneDirector.pause();
        pauseMenuUIGroup.hide();
        pauseMenuBtnUIGroup.hide();
        undoBtnUIGroup.hide();
        winMenuTitleText.setText(WIN_MENU_TEXT.replace("{}", player.getName()));
        addAnimator(winMenuAnimator);
        winMenuUIGroup.show();
    }
}
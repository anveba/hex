package main.hex.ui;

import main.engine.*;
import main.engine.font.BitmapFont;
import main.engine.format.TimeFormat;
import main.engine.graphics.*;
import main.engine.io.ResourceManager;
import main.engine.ui.*;
import main.engine.ui.animation.AnimationSequence;
import main.engine.ui.animation.Animator;
import main.engine.ui.animation.Ease;
import main.engine.ui.animation.Hide;
import main.engine.ui.animation.Wait;
import main.engine.ui.animation.easing.CubicIn;
import main.engine.ui.animation.easing.CubicInOut;
import main.engine.ui.callback.ButtonCallback;
import main.hex.HexException;
import main.hex.board.Board;
import main.hex.board.TileColour;
import main.hex.logic.GameCustomisation;
import main.hex.logic.GameLogic;
import main.hex.player.AIPlayer;
import main.hex.player.Player;
import main.hex.player.PlayerSkin;
import main.hex.player.PlayerType;
import main.hex.player.UserPlayer;
import main.hex.resources.SkinDatabase;
import main.hex.resources.TextureLibrary;
import main.hex.scene.GameplayScene;
import main.hex.scene.SceneDirector;


/**
 * Represents a frame that allows the user to customise a game's settings.
 * This frame mainly consists of UI elements, and is tightly coupled with the logic class "StartGameFrameLogic".
 * As this class does not contain any logic significant logic, and is mainly UI, it is tested by manual inspection.
 *
 * @Author Oliver Grønborg Christensen - s204479
 * @Author Oliver Siggaard - s204450 (Texture/Color caoursel)
 */

public class StartGameFrame extends Frame {

	//FONTS:
	private BitmapFont FONT_ROBOTO = ResourceManager.getInstance().loadFont("fonts/roboto.ttf");
	private BitmapFont FONT_FREDOKA_ONE = ResourceManager.getInstance().loadFont("fonts/fredoka-one.one-regular.ttf");

	//STRINGS
	private final String FRAME_TITLE = "Game Settings";
	private final String START_GAME_BTN_TEXT = "Start Game";
	private final String GAME_SETTINGS_SIZE_TEXT = "Board Size:";
	private final String GAME_SETTINGS_TIME_TEXT = "Time Limit:";
	private final String GAME_SETTINGS_SWAP_RULE_TEXT = "Swap Rule:";
	private final String ENABLED_TEXT = "Enabled";
	private final String DISABLED_TEXT = "Disabled";
	private final String PLAYER1_TITLE = "Player 1";
	private final String PLAYER2_TITLE = "Player 2";
	private final String PLAYER_NAME_LABEL = "Name:";
	private final String BOARD_SIZE_LABEL = "Board Size: {}x{}";
	private final String GAME_TIME_LABEL = "{} mm:ss";

	private final float standardFontSize = 0.07f;
	private final float startBtnFontSize = 0.10f;
	private final float playerFontSize = 0.08f;
	private final float skinCarouselFontSize = 0.06f;
	private final float headerFontSize = 0.12f;
	private final float playerTypeFontSize = 0.05f;
	
	private Image blackOutImage;
	private RectButton startGameBtn;

	//LOGIC
	private StartGameFrameLogic startGameFrameLogic;

	//Constructors
	public StartGameFrame() {
		startGameFrameLogic = new StartGameFrameLogic();

		startGameFrameLogic.addHexTextureId(SkinDatabase.defaultTextureId, "Basic");
		startGameFrameLogic.addHexTextureId(SkinDatabase.zebraTextureId, "Zebra");
		startGameFrameLogic.addHexTextureId(SkinDatabase.largeBubbleTextureId, "Large Bubble");
		startGameFrameLogic.addHexTextureId(SkinDatabase.smallBubbleTextureId, "Small Bubble");
		startGameFrameLogic.addHexTextureId(SkinDatabase.abstractTextureId, "Abstract");
		startGameFrameLogic.addHexTextureId(SkinDatabase.duckTextureId, "Duck");
		startGameFrameLogic.setPlayerTextureIndex(1,0);

		startGameFrameLogic.addHexColour(Colour.Red, "Red");
		startGameFrameLogic.addHexColour(Colour.Pink, "Pink");
		startGameFrameLogic.addHexColour(Colour.Purple, "Purple");
		startGameFrameLogic.addHexColour(Colour.Blue, "Blue");
		startGameFrameLogic.addHexColour(Colour.Aqua, "Aqua");
		startGameFrameLogic.addHexColour(Colour.Green, "Green");
		startGameFrameLogic.addHexColour(Colour.Yellow, "Yellow");
		startGameFrameLogic.addHexColour(Colour.Orange, "Orange");
		startGameFrameLogic.setPlayerColourIndex(1, 3);

		startGameFrameLogic.addPlayerType(PlayerType.HUMAN, "Human Opponent");
		startGameFrameLogic.addPlayerType(PlayerType.AI_EASY, "AI Opponent");
		//startGameFrameLogic.setPlayerTypeIndex(1, 1); //Sets player 2 to initially be an AI.

		UIGroup root = new UIGroup(0.0f, 0.0f);
		initializeFrameView(root);
		setRoot(root);
	}

	private void initializeFrameView(UIGroup root) {

		UIGroup settingsMenu = new UIGroup(0.0f, 0.0f);
		root.addChild(settingsMenu);

		settingsMenu.addChild(createBackground());
		settingsMenu.addChild(createGameSettings());
		settingsMenu.addChild(createPlayerSettings());


		RectButton backToMainMenuBtn = new RectButton(-0.75f, 0.68f, 0.1f, 0.1f, TextureLibrary.BLANK_ARROW_LEFT.getTexture(),
				FONT_FREDOKA_ONE,"", standardFontSize, args -> backToMainMenu(), null, null);
		settingsMenu.addChild(backToMainMenuBtn);

		startGameBtn = new RectButton(0.0f, -0.8f, 0.55f, 0.18f, TextureLibrary.BUTTON_TEXT_LARGE_ORANGE_ROUND.getTexture(),
				FONT_FREDOKA_ONE, START_GAME_BTN_TEXT, startBtnFontSize, args -> startGame(), null, null);
		settingsMenu.addChild(startGameBtn);
		
		blackOutImage = new Image(0.0f, 0.0f, 50.0f, 2.0f, 
        		TextureLibrary.WHITE_PX.getTexture(), Colour.Black);
		blackOutImage.hide();
		root.addChild(blackOutImage);
	}
	
	private void fadeOut(float time, Runnable onEnd) {
		if (time <= 0.0f)
    		throw new HexException("Time was not positive");
    	blackOutImage.show();
    	AnimationSequence anim = new AnimationSequence(
    			new Ease(blackOutImage, new CubicInOut(), 
    					0.0f, 2.0f, 0.0f, 0.0f,
    					time)
    			);
    	anim.setOnEndAction(onEnd);
    	addAnimator(new Animator(anim));
	}

	private UIGroup createBackground() {
		UIGroup backgroundUIGroup = new UIGroup(0.0f, 0.0f);
		Image background = new Image(0.0f, -0.075f, 1.8f, 1.8f, TextureLibrary.GREY_SQUARE_BOX.getTexture());
		backgroundUIGroup.addChild(background);

		//Background + Banner + Banner Text
		UIGroup banner = new UIGroup(0.0f, 0.78f);
		Image bannerBackground = new Image(0.0f, 0.0f, 1.0f, 0.4f, TextureLibrary.BANNER_GREY.getTexture());
		Text bannerText = new Text(00.0f, 0.02f, FONT_FREDOKA_ONE, FRAME_TITLE, headerFontSize);
		banner.addChild(bannerBackground);
		banner.addChild(bannerText);

		backgroundUIGroup.addChild(banner);

		return backgroundUIGroup;
	}

	private UIGroup createGameSettings() {
		UIGroup gameSettings = new UIGroup(-0.6f, 0.5f);

		//Board Size
		UIGroup boardSize = new UIGroup(0.0f, 0.0f);
		gameSettings.addChild(boardSize);

		Text sizeText = new Text(-0.1f, 0.0f, FONT_FREDOKA_ONE, GAME_SETTINGS_SIZE_TEXT, standardFontSize);
		boardSize.addChild(sizeText);
		sizeText.setAnchorPoint(AnchorPoint.Left);

		Slider boardSizeSlider = new Slider(0.55f, -0.015f, 0.6f,0.06f, TextureLibrary.SCROLLBAR_GREY.getTexture(),TextureLibrary.SCROLLBAR_BUTTON_GREY.getTexture(), 3, 11, 11,null);
		boardSize.addChild(boardSizeSlider);
		startGameFrameLogic.setBoardSizeSlider(boardSizeSlider);
		//Creating slider text object(optional):
		Text boardSizeSliderText = new Text(boardSizeSlider.getWidth()/2f + 0.05f, boardSizeSlider.getY() + 0.015f, FONT_FREDOKA_ONE, BOARD_SIZE_LABEL, standardFontSize - 0.01f);
		boardSizeSliderText.setColour(Colour.Grey);
		boardSizeSliderText.setAnchorPoint(AnchorPoint.Left);
		boardSizeSlider.setText(boardSizeSliderText);


		//Time Limit
		UIGroup timeLimit = new UIGroup(-0.0f, -0.13f);
		gameSettings.addChild(timeLimit);

		Text timeText = new Text(-0.1f,0, FONT_FREDOKA_ONE, GAME_SETTINGS_TIME_TEXT, standardFontSize);
		timeLimit.addChild(timeText);
		timeText.setAnchorPoint(AnchorPoint.Left);

		Slider timeLimitSlider = new Slider(0.55f, -0.015f, 0.6f,0.06f, TextureLibrary.SCROLLBAR_GREY.getTexture(),TextureLibrary.SCROLLBAR_BUTTON_GREY.getTexture(), 10, 600, 300, null);
		timeLimit.addChild(timeLimitSlider);
		startGameFrameLogic.setTurnTimeSlider(timeLimitSlider);
		//Creating slider text object(optional):
		Text timeLimitSliderText = new Text(timeLimitSlider.getWidth()/2f + 0.05f, timeLimitSlider.getY() + 0.015f, FONT_FREDOKA_ONE, GAME_TIME_LABEL, standardFontSize - 0.01f);
		timeLimitSliderText.setColour(Colour.Grey);
		timeLimitSliderText.setAnchorPoint(AnchorPoint.Left);
		timeLimitSlider.setText(timeLimitSliderText);
		timeLimitSlider.setFormat(new TimeFormat());


		//Swap Rule
		UIGroup swapRuleUIGroup = new UIGroup(-0.1f, -0.26f);
		gameSettings.addChild(swapRuleUIGroup);

		Text swapRuleText = new Text(0.0f, 0.0f, FONT_FREDOKA_ONE, GAME_SETTINGS_SWAP_RULE_TEXT, standardFontSize);
		swapRuleUIGroup.addChild(swapRuleText);
		swapRuleText.setAnchorPoint(AnchorPoint.Left);

		Text swapRuleDisabledText = new Text(0.47f, 0.0f, FONT_FREDOKA_ONE, DISABLED_TEXT, playerTypeFontSize);
		swapRuleUIGroup.addChild(swapRuleDisabledText);

		Text swapRuleEnabledText = new Text(0.82f, 0.0f, FONT_FREDOKA_ONE, ENABLED_TEXT, playerTypeFontSize);
		swapRuleUIGroup.addChild(swapRuleEnabledText);

		ToggleSwitch swapRuleToggleSwitch = new ToggleSwitch(0.65f, -0.01f, 0.15f, 0.07f,
				false,
				Colour.Red, Colour.Green,
				(args) -> toggleSwapRuleClicked(), (args) -> toggleSwapRuleClicked(),
				null, null);


		swapRuleUIGroup.addChild(swapRuleToggleSwitch);

		return gameSettings;
	}

	private void toggleSwapRuleClicked() {
		startGameFrameLogic.toggleSwapRule();
	}

	private UIGroup createPlayerSettings() {
		UIGroup playerSettingsUIGroup = new UIGroup(0.0f, -0.05f);

		playerSettingsUIGroup.addChild(createPlayerSetting(-0.45f, 0.14f, PLAYER1_TITLE, 0));
		playerSettingsUIGroup.addChild(createPlayerSetting(0.45f, 0.14f, PLAYER2_TITLE, 1));

		return playerSettingsUIGroup;
	}

	private UIGroup createPlayerSetting(float x,float y, String title, int playerIndex) {
		UIGroup playerSettingUIGroup = new UIGroup(x, y);
		//Title
		Text titleText = new Text(0.0f, -0.02f, FONT_FREDOKA_ONE, title, playerFontSize);
		playerSettingUIGroup.addChild(titleText);

		/*
		  Skin Selection (Texture and Colour)
		 */
		UIGroup skinCarouselUIGroup = new UIGroup(0.0f, -0.2f);
		// skinImage showcase
		float c = (float)Math.cos(Math.toRadians(30.0f));
		Image skinImage = new Image(0.0f, -0.235f, 0.22f * c, 0.22f, 
				SkinDatabase.getInstance().getTextureFromId(startGameFrameLogic.getHexTextureId(0)), 
				startGameFrameLogic.getPlayerColour(playerIndex));
		skinCarouselUIGroup.addChild(skinImage);

		// Texture carousel text
		Text textureCarouselText = new Text(0.0f, 0.06f, FONT_FREDOKA_ONE,
				startGameFrameLogic.getPlayerTextureName(playerIndex), skinCarouselFontSize);
		skinCarouselUIGroup.addChild(textureCarouselText);
		// Texture carousel left arrow
		skinCarouselUIGroup.addChild(carouselButton(-0.22f, 0.05f, 0.07f,
				TextureLibrary.LEFT_CAROUSEL_ARROW.getTexture(),
				(args) -> textureCarouselLeft(skinImage, textureCarouselText, playerIndex)));
		// Texture carousel right arrow
		skinCarouselUIGroup.addChild(carouselButton(0.22f, 0.05f, 0.07f,
				TextureLibrary.RIGHT_CAROUSEL_ARROW.getTexture(),
				(args) -> textureCarouselRight(skinImage, textureCarouselText, playerIndex)));

		// Texture carousel text
		Text colourCarouselText = new Text(0.0f, -0.04f, FONT_FREDOKA_ONE,
				startGameFrameLogic.getPlayerColourString(playerIndex), skinCarouselFontSize);
		skinCarouselUIGroup.addChild(colourCarouselText);
		// Colour carousel left arrow
		skinCarouselUIGroup.addChild(carouselButton(-0.22f, -0.05f, 0.07f,
				TextureLibrary.LEFT_CAROUSEL_ARROW.getTexture(),
				(args) -> colourCarouselLeft(skinImage, colourCarouselText, playerIndex)));
		// Colour carousel right arrow
		skinCarouselUIGroup.addChild(carouselButton(0.22f, -0.05f, 0.07f,
				TextureLibrary.RIGHT_CAROUSEL_ARROW.getTexture(),
				(args) -> colourCarouselRight(skinImage, colourCarouselText, playerIndex)));

		playerSettingUIGroup.addChild(skinCarouselUIGroup);

		/*
		  Player Name
		 */
		Text nameText = new Text(-0.24f, -0.60f, FONT_FREDOKA_ONE, PLAYER_NAME_LABEL, standardFontSize);
		playerSettingUIGroup.addChild(nameText);
		TextField playerNameTextField = new TextField(0.105f, -0.60f, FONT_FREDOKA_ONE, "Player " + (playerIndex + 1),0.49f, 0.06f, Colour.LightGrey);
		startGameFrameLogic.setPlayerName(playerIndex, playerNameTextField);
		playerSettingUIGroup.addChild(playerNameTextField);

		/*
		  Player Type Carousel
		 */
		UIGroup typeCarouselUIGroup = new UIGroup(0.0f, -0.7f);
		//text
		Text typeText = new Text(0.0f, 0.0f, FONT_FREDOKA_ONE, startGameFrameLogic.getPlayerTypeString(playerIndex), playerTypeFontSize);
		typeCarouselUIGroup.addChild(typeText);

		// Player type carousel left arrow
		typeCarouselUIGroup.addChild(carouselButton(-0.32f, 0.0f, 0.06f,
				TextureLibrary.LEFT_CAROUSEL_ARROW.getTexture(),
				(args) -> playerTypeLeft(typeText, playerIndex)));

		// Player type carousel right arrow
		typeCarouselUIGroup.addChild(carouselButton(0.32f, 0.0f, 0.06f,
				TextureLibrary.RIGHT_CAROUSEL_ARROW.getTexture(),
				(args) -> playerTypeRight(typeText, playerIndex)));

		playerSettingUIGroup.addChild(typeCarouselUIGroup);

		return playerSettingUIGroup;
	}

	// A fast way to create a carouselButton
	public RectButton carouselButton(float x, float y, float size, Texture texture, ButtonCallback callback) {
		return new RectButton(x, y, size, size, texture, FONT_ROBOTO, "", standardFontSize, callback,
				null, null);
	}

	public void textureCarouselLeft(Image skinImage, Text textureText, int playerIndex) {
		startGameFrameLogic.previousTexture(playerIndex);
		textureText.setText(startGameFrameLogic.getPlayerTextureName(playerIndex));
		Texture texture = SkinDatabase.getInstance().getTextureFromId(startGameFrameLogic.getHexTextureId(playerIndex));
		skinImage.setTexture(texture);
	}

	public void textureCarouselRight(Image skinImage, Text textureText, int playerIndex) {
		startGameFrameLogic.nextTexture(playerIndex);
		textureText.setText(startGameFrameLogic.getPlayerTextureName(playerIndex));
		Texture texture = SkinDatabase.getInstance().getTextureFromId(startGameFrameLogic.getHexTextureId(playerIndex));
		skinImage.setTexture(texture);
	}

	public void colourCarouselLeft(Image skinImage, Text colourText, int playerIndex) {
		startGameFrameLogic.previousColour(playerIndex);
		colourText.setText(startGameFrameLogic.getPlayerColourString(playerIndex));
		skinImage.setColour(startGameFrameLogic.getHexColour(startGameFrameLogic.getPlayerColourIndex(playerIndex)));
	}

	public void colourCarouselRight(Image skinImage, Text colourText, int playerIndex) {
		startGameFrameLogic.nextColour(playerIndex);
		colourText.setText(startGameFrameLogic.getPlayerColourString(playerIndex));
		skinImage.setColour(startGameFrameLogic.getHexColour(startGameFrameLogic.getPlayerColourIndex(playerIndex)));
	}

	public void playerTypeLeft(Text typeText, int playerIndex) {
		startGameFrameLogic.previousPlayerType(playerIndex);
		typeText.setText(startGameFrameLogic.getPlayerTypeString(playerIndex));
	}

	public void playerTypeRight(Text typeText, int playerIndex) {
		startGameFrameLogic.nextPlayerType(playerIndex);
		typeText.setText(startGameFrameLogic.getPlayerTypeString(playerIndex));
	}

	private void startGame() {
		startGameBtn.disable();
		fadeOut(1.0f, () -> {
			String p1Name = startGameFrameLogic.getPlayerName(0);
			String p2Name = startGameFrameLogic.getPlayerName(1);
	
			GameCustomisation gameCustomisation = new GameCustomisation(
					p1Name,
					p2Name,
					
					new PlayerSkin(startGameFrameLogic.getPlayerTextureId(0), startGameFrameLogic.getPlayerColour(0)),
					new PlayerSkin(startGameFrameLogic.getPlayerTextureId(1), startGameFrameLogic.getPlayerColour(1)),
					startGameFrameLogic.getTurnTime(),
					startGameFrameLogic.getSwapRule());
	
			int timeLimit = startGameFrameLogic.getTurnTime();
	
			Board b = new Board(startGameFrameLogic.getBoardSize());
			Player p1 = (startGameFrameLogic.getPlayerType(0) == PlayerType.HUMAN)  ?
					new UserPlayer(TileColour.PLAYER1, timeLimit, p1Name) : new AIPlayer(TileColour.PLAYER1, timeLimit, AIPlayer.defaultMaximumProcessingTime, p1Name);
			Player p2 = (startGameFrameLogic.getPlayerType(1) == PlayerType.HUMAN)  ?
					new UserPlayer(TileColour.PLAYER2, timeLimit, p2Name) : new AIPlayer(TileColour.PLAYER2, timeLimit, AIPlayer.defaultMaximumProcessingTime, p2Name);
	
			SceneDirector.changeScene(
					new GameplayScene(
							new GameLogic(b, p1, p2, gameCustomisation.getSwapRule()),
							gameCustomisation));
		});
	}

	public boolean getSwapRule() {
		return startGameFrameLogic.getSwapRule();
	}

	public void backToMainMenu() {
		AnimationSequence anim = new AnimationSequence(
				new Ease(getRoot(), new CubicIn(),
						0.0f, 0.0f, 0.0f, 2.0f, 
						1.0f),
				new Wait(2.0f)
				);
		anim.setOnEndAction(() -> {
			FrameStack.getInstance().push(new MainMenuFrame()); getRoot().hide();
		});
		addAnimator(new Animator(anim));
	}
}
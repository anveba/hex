package main.hex.ui;

import main.engine.*;
import main.engine.font.BitmapFont;
import main.engine.graphics.*;
import main.engine.ui.*;
import main.hex.GameCustomisation;
import main.hex.GameLogic;
import main.hex.board.Board;
import main.hex.board.TileColour;
import main.hex.player.AIPlayer;
import main.hex.player.Player;
import main.hex.player.PlayerSkin;
import main.hex.player.PlayerType;
import main.hex.player.UserPlayer;
import main.hex.resources.TextureLibrary;
import main.hex.scene.GameplayScene;
import main.hex.scene.SceneDirector;

public class StartGameFrame extends Frame {

	//FONTS:
	private BitmapFont FONT_ROBOTO = ResourceManager.getInstance().loadFont("fonts/roboto.ttf");
	private BitmapFont FONT_FREDOKA_ONE = ResourceManager.getInstance().loadFont("fonts/fredoka-one.one-regular.ttf");

	//STRINGS
	private final String FRAME_TITLE = "Game Settings";
	private final String START_GAME_BTN_TEXT = "Start Game";
	private final String GAME_SETTINGS_SIZE_TEXT = "Board Size:";
	private final String GAME_SETTINGS_TIME_TEXT = "Time Limit:";
	private final String GAME_SETTINGS_SWAP_RULE_TEXT = "Enable Swap Rule:";
	private final String PLAYER1_TITLE = "Player 1";
	private final String PLAYER2_TITLE = "Player 2";
	private final String PLAYER_NAME_LABEL = "Name:";
	private final String BOARD_SIZE_LABEL = "Board Size: {}x{}";
	private final String GAME_TIME_LABEL = "{} Seconds";

	private float fontSize = 0.07f;
	private float headerFontSize = 0.12f;
	private float playerTypeFontSize = 0.05f;

	//LOGIC

	private StartGameFrameLogic startGameFrameLogic;

	//Constructors
	public StartGameFrame() {

		startGameFrameLogic = new StartGameFrameLogic();

		startGameFrameLogic.addHexTexture(TextureLibrary.WHITE_TILE.getTexture());
		startGameFrameLogic.addHexTexture(TextureLibrary.ZEBRA_TILE.getTexture());
		startGameFrameLogic.setPlayerTextureIndex(1,0);

		startGameFrameLogic.setPlayer1Col(Colour.Red); // TODO: Colour to be chosen by player in gui
		startGameFrameLogic.setPlayer2Col(Colour.Blue); // TODO: Colour to be chosen by player in gui

		startGameFrameLogic.addPlayerType(PlayerType.HUMAN, "Human Opponent");
		startGameFrameLogic.addPlayerType(PlayerType.AI_EASY, "AI Opponent - Easy");
		startGameFrameLogic.addPlayerType(PlayerType.AI_NORMAL, "AI Opponent - Normal");
		startGameFrameLogic.addPlayerType(PlayerType.AI_HARD, "AI Opponent - Hard");
		startGameFrameLogic.setPlayerTypeIndex(0, 2);


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

		//Start Game Button
		ButtonCallback startGameBtnClicked = (args) -> {
			System.out.println("Game started!");
			startGame();
		};
		RectButton startGameBtn = new RectButton(0.0f, -0.8f, 0.5f, 0.18f, TextureLibrary.ORANGE_BUTTON.getTexture(),
				FONT_FREDOKA_ONE, START_GAME_BTN_TEXT, fontSize, startGameBtnClicked, null, null);
		settingsMenu.addChild(startGameBtn);
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
		UIGroup gameSettings = new UIGroup(-0.6f, 0.45f);

		//Board Size
		UIGroup boardSize = new UIGroup(0.0f, 0.0f);
		gameSettings.addChild(boardSize);

		Text sizeText = new Text(-0.1f, 0.0f, FONT_FREDOKA_ONE, GAME_SETTINGS_SIZE_TEXT, fontSize);
		boardSize.addChild(sizeText);
		sizeText.setAnchorPoint(AnchorPoint.Left);

		Slider boardSizeSlider = new Slider(0.55f, -0.015f, 0.6f,0.06f, TextureLibrary.SCROLLBAR_GREY.getTexture(),TextureLibrary.SCROLLBAR_BUTTON_GREY.getTexture(), 3, 25, 11);
		boardSize.addChild(boardSizeSlider);
		startGameFrameLogic.setBoardSizeSlider(boardSizeSlider);
		//Creating slider text object(optional):
		Text boardSizeSliderText = new Text(boardSizeSlider.getX() + boardSizeSlider.getWidth()/2f + 0.05f, boardSizeSlider.getY() + 0.015f, FONT_FREDOKA_ONE, BOARD_SIZE_LABEL, fontSize - 0.01f);
		boardSizeSliderText.setColour(Colour.Grey);
		boardSizeSliderText.setAnchorPoint(AnchorPoint.Left);
		boardSizeSlider.setText(boardSizeSliderText);


		//Time Limit
		UIGroup timeLimit = new UIGroup(-0.0f, -0.13f);
		gameSettings.addChild(timeLimit);

		Text timeText = new Text(-0.1f,0, FONT_FREDOKA_ONE, GAME_SETTINGS_TIME_TEXT, fontSize);
		timeLimit.addChild(timeText);
		timeText.setAnchorPoint(AnchorPoint.Left);

		Slider timeLimitSlider = new Slider(0.55f, -0.015f, 0.6f,0.06f, TextureLibrary.SCROLLBAR_GREY.getTexture(),TextureLibrary.SCROLLBAR_BUTTON_GREY.getTexture(), 60, 600, 300);
		timeLimit.addChild(timeLimitSlider);
		startGameFrameLogic.setTurnTimeSlider(timeLimitSlider);
		//Creating slider text object(optional):
		Text timeLimitSliderText = new Text(timeLimitSlider.getX() + timeLimitSlider.getWidth()/2f + 0.05f, timeLimitSlider.getY() + 0.015f, FONT_FREDOKA_ONE, GAME_TIME_LABEL, fontSize - 0.01f);
		timeLimitSliderText.setColour(Colour.Grey);
		timeLimitSliderText.setAnchorPoint(AnchorPoint.Left);
		timeLimitSlider.setText(timeLimitSliderText);


		//Swap Rule
		UIGroup swapRuleUIGroup = new UIGroup(-0.1f, -0.26f);
		gameSettings.addChild(swapRuleUIGroup);

		Text swapRuleText = new Text(0.0f, 0.0f, FONT_FREDOKA_ONE, GAME_SETTINGS_SWAP_RULE_TEXT, fontSize);
		swapRuleUIGroup.addChild(swapRuleText);
		swapRuleText.setAnchorPoint(AnchorPoint.Left);

		RectButton swapRuleBtn = new RectButton(0.75f, -0.01f, 0.25f, 0.1f, TextureLibrary.ORANGE_NO_BUTTON.getTexture(),
				FONT_ROBOTO, "", fontSize, null, null, null);

		ButtonCallback swapruleBtnClicked = (args) -> {
			toggleSwapRule(swapRuleBtn);
		};
		swapRuleBtn.setClickCallback(swapruleBtnClicked);
		swapRuleUIGroup.addChild(swapRuleBtn);




		return gameSettings;
	}

	public void toggleSwapRule(RectButton swapRuleBtn) {
		startGameFrameLogic.toggleSwapRule();

		if(startGameFrameLogic.getSwapRule()) {
			swapRuleBtn.updateImageTexture(TextureLibrary.GREEN_YES_BUTTON.getTexture());
		} else {
			swapRuleBtn.updateImageTexture(TextureLibrary.ORANGE_NO_BUTTON.getTexture());
		}
	}



	private UIGroup createPlayerSettings() {
		UIGroup playerSettingsUIGroup = new UIGroup(0.0f, -0.05f);

		playerSettingsUIGroup.addChild(createPlayerSetting(-0.45f, 0.0f, PLAYER1_TITLE, 0, startGameFrameLogic.getPlayer1Col()));
		playerSettingsUIGroup.addChild(createPlayerSetting(0.45f, 0.0f, PLAYER2_TITLE, 1, startGameFrameLogic.getPlayer2Col()));


		return playerSettingsUIGroup;
	}

	private UIGroup createPlayerSetting(float x,float y, String title, int playerIndex, Colour playerCol) {
		UIGroup playerSettingUIGroup = new UIGroup(x, y);
		//Title
		Text titleText = new Text(0.0f, 0.0f, FONT_FREDOKA_ONE, title, fontSize);
		playerSettingUIGroup.addChild(titleText);


		/**
		 * Texture Carousel
		 */
		UIGroup colorCarouselUIGroup = new UIGroup(0.0f, -0.2f);

		float c = (float)Math.cos(Math.toRadians(30.0f));
		
		//texture
		Image colorImage = new Image(0.0f, 0.0f, 0.2f * c, 0.2f, startGameFrameLogic.getHexTexture(0), playerCol);
		colorCarouselUIGroup.addChild(colorImage);

		//left arrow
		ButtonCallback leftClicked = (args) -> {
			carouselLeft(colorImage, playerIndex);
		};
		RectButton leftCarouselArrow = new RectButton(-0.25f, 0.0f, 0.08f, 0.08f, TextureLibrary.LEFT_CAROUSEL_ARROW.getTexture(),
				FONT_ROBOTO, "", fontSize, leftClicked, null, null);
		colorCarouselUIGroup.addChild(leftCarouselArrow);

		//right arrow
		ButtonCallback rightClicked = (args) -> {
			carouselRight(colorImage, playerIndex);
		};
		RectButton rightCarouselArrow = new RectButton(0.25f, 0.0f, 0.08f, 0.08f, TextureLibrary.RIGHT_CAROUSEL_ARROW.getTexture(),
				FONT_ROBOTO, "", fontSize, rightClicked, null, null);
		colorCarouselUIGroup.addChild(rightCarouselArrow);

		playerSettingUIGroup.addChild(colorCarouselUIGroup);


		//Name text field
		Text nameText = new Text(-0.24f, -0.40f, FONT_FREDOKA_ONE, PLAYER_NAME_LABEL, fontSize);
		playerSettingUIGroup.addChild(nameText);
		TextField playerNameTextField = new TextField(0.105f, -0.40f, FONT_FREDOKA_ONE, "Player " + (playerIndex + 1),0.49f, 0.06f, Colour.LightGrey);
		startGameFrameLogic.setPlayerName(playerIndex, playerNameTextField);
		playerSettingUIGroup.addChild(playerNameTextField);


		/**
		 * Type Carousel
		 */
		UIGroup typeCarouselUIGroup = new UIGroup(0.0f, -0.5f);

		//text
		Text typeText = new Text(0.0f, 0.0f, FONT_FREDOKA_ONE, startGameFrameLogic.getPlayerTypeString(playerIndex), playerTypeFontSize);
		typeCarouselUIGroup.addChild(typeText);

		//left arrow
		ButtonCallback typeLeftClicked = (args) -> {
			playerTypeLeft(typeText, playerIndex);
		};
		RectButton typeLeftCarouselArrow = new RectButton(-0.32f, 0.0f, 0.06f, 0.06f, TextureLibrary.LEFT_CAROUSEL_ARROW.getTexture(),
				FONT_ROBOTO, "", fontSize, typeLeftClicked, null, null);
		typeCarouselUIGroup.addChild(typeLeftCarouselArrow);

		//right arrow
		ButtonCallback typeRightClicked = (args) -> {
			playerTypeRight(typeText, playerIndex);
		};
		RectButton typeRightCarouselArrow = new RectButton(0.32f, 0.0f, 0.06f, 0.06f, TextureLibrary.RIGHT_CAROUSEL_ARROW.getTexture(),
				FONT_ROBOTO, "", fontSize, typeRightClicked, null, null);
		typeCarouselUIGroup.addChild(typeRightCarouselArrow);

		playerSettingUIGroup.addChild(typeCarouselUIGroup);


		return playerSettingUIGroup;
	}

	public void carouselLeft(Image colorImage, int playerIndex) {
		startGameFrameLogic.previousTexture(playerIndex);
		colorImage.setTexture(startGameFrameLogic.getHexTexture(startGameFrameLogic.getPlayerTextureIndex(playerIndex)));
	}

	public void carouselRight(Image colorImage, int playerIndex) {
		startGameFrameLogic.nextTexture(playerIndex);
		colorImage.setTexture(startGameFrameLogic.getHexTexture(startGameFrameLogic.getPlayerTextureIndex(playerIndex)));
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
		GameCustomisation gameCustomisation = new GameCustomisation(
				startGameFrameLogic.getPlayerName(0),
				startGameFrameLogic.getPlayerName(1),
				new PlayerSkin(startGameFrameLogic.getPlayerTexture(0), startGameFrameLogic.getPlayer1Col()),
				new PlayerSkin(startGameFrameLogic.getPlayerTexture(1), startGameFrameLogic.getPlayer2Col()),
				startGameFrameLogic.getTurnTime(),
				startGameFrameLogic.getSwapRule());

		int timeLimit = startGameFrameLogic.getTurnTime();

		Board b = new Board(startGameFrameLogic.getBoardSize());
		Player p1 = (startGameFrameLogic.getPlayerType(0) == PlayerType.HUMAN)  ?
				new UserPlayer(TileColour.PLAYER1, timeLimit) : new AIPlayer(TileColour.PLAYER1, timeLimit, 3);
		Player p2 = (startGameFrameLogic.getPlayerType(1) == PlayerType.HUMAN)  ?
				new UserPlayer(TileColour.PLAYER2, timeLimit) : new AIPlayer(TileColour.PLAYER2, timeLimit, 3);

		SceneDirector.changeScene(
				new GameplayScene(
						new GameLogic(b, p1, p2),
						gameCustomisation));
	}

	public boolean getSwapRule() {
		return startGameFrameLogic.getSwapRule();
	}

	public Texture getHexSkin(int playerIndex) {
		return startGameFrameLogic.getHexTexture(startGameFrameLogic.getPlayerTextureIndex(playerIndex));
	}
}

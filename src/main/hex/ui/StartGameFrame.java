package main.hex.ui;

import main.engine.*;
import main.engine.font.BitmapFont;
import main.engine.graphics.*;
import main.engine.ui.*;
import main.hex.AIPlayer;
import main.hex.GameLogic;
import main.hex.Player;
import main.hex.UserPlayer;
import main.hex.board.Board;
import main.hex.board.Tile;
import main.hex.board.TileColour;
import main.hex.player.PlayerType;
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

	//LOGIC

	private StartGameFrameLogic logic;
	//Constructors
	public StartGameFrame() {

		logic = new StartGameFrameLogic();

		logic.addHexSkin(TextureLibrary.BLUE_TILE.getTexture());
		logic.addHexSkin(TextureLibrary.RED_TILE.getTexture());
		logic.addHexSkin(TextureLibrary.YELLOW_TILE.getTexture());
		logic.addHexSkin(TextureLibrary.ZEBRA_TILE.getTexture());
		logic.setPlayerSkinIndex(1,1);

		logic.addPlayerType(PlayerType.HUMAN, "Human Opponent");
		logic.addPlayerType(PlayerType.AI_EASY, "AI Opponent - Easy");
		logic.addPlayerType(PlayerType.AI_NORMAL, "AI Opponent - Normal");
		logic.addPlayerType(PlayerType.AI_HARD, "AI Opponent - Hard");
		logic.setPlayerTypeIndex(1, 2);


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
				FONT_FREDOKA_ONE, START_GAME_BTN_TEXT, 0.055f, startGameBtnClicked, null, null);
		settingsMenu.addChild(startGameBtn);
	}

	private UIGroup createBackground() {
		UIGroup backgroundUIGroup = new UIGroup(0.0f, 0.0f);
		Image background = new Image(0.0f, -0.075f, 1.8f, 1.8f, TextureLibrary.GREY_SQUARE_BOX.getTexture());
		backgroundUIGroup.addChild(background);

		//Background + Banner + Banner Text
		UIGroup banner = new UIGroup(0.0f, 0.78f);
		Image bannerBackground = new Image(0.0f, 0.0f, 1.0f, 0.4f, TextureLibrary.BANNER_GREY.getTexture());
		Text bannerText = new Text(00.0f, 0.02f, FONT_FREDOKA_ONE, FRAME_TITLE, 0.1f);
		banner.addChild(bannerBackground);
		banner.addChild(bannerText);

		backgroundUIGroup.addChild(banner);

		return backgroundUIGroup;
	}

	private UIGroup createGameSettings() {
		UIGroup gameSettings = new UIGroup(-0.6f, 0.45f);

		//Board Size
		Text SizeText = new Text(0.0f, 0.0f, FONT_FREDOKA_ONE, GAME_SETTINGS_SIZE_TEXT, 0.05f);
		gameSettings.addChild(SizeText);

		//Time Limit
		Text TimeText = new Text(0.0f, -0.1f, FONT_FREDOKA_ONE, GAME_SETTINGS_TIME_TEXT, 0.05f);
		gameSettings.addChild(TimeText);

		//Swap Rule
		UIGroup swapRuleUIGroup = new UIGroup(0.11f, -0.2f);

		Text SwapRuleText = new Text(0.0f, 0.0f, FONT_FREDOKA_ONE, GAME_SETTINGS_SWAP_RULE_TEXT, 0.05f);
		swapRuleUIGroup.addChild(SwapRuleText);

		RectButton swapRuleBtn = new RectButton(0.45f, -0.01f, 0.25f, 0.1f, TextureLibrary.ORANGE_NO_BUTTON.getTexture(),
				FONT_ROBOTO, "", 0.05f, null, null, null);

		ButtonCallback swapruleBtnClicked = (args) -> {
			toggleSwapRule(swapRuleBtn);
		};
		swapRuleBtn.setClickCallback(swapruleBtnClicked);
		swapRuleUIGroup.addChild(swapRuleBtn);


		gameSettings.addChild(swapRuleUIGroup);

		return gameSettings;
	}

	public void toggleSwapRule(RectButton swapRuleBtn) {
		logic.toggleSwapRule();

		if(logic.getSwapRule()) {
			swapRuleBtn.updateImageTexture(TextureLibrary.GREEN_YES_BUTTON.getTexture());
		} else {
			swapRuleBtn.updateImageTexture(TextureLibrary.ORANGE_NO_BUTTON.getTexture());
		}
	}



	private UIGroup createPlayerSettings() {
		UIGroup playerSettingsUIGroup = new UIGroup(0.0f, 0.05f);

		playerSettingsUIGroup.addChild(createPlayerSetting(-0.45f, 0.0f, PLAYER1_TITLE, 0));
		playerSettingsUIGroup.addChild(createPlayerSetting(0.45f, 0.0f, PLAYER2_TITLE, 1));


		return playerSettingsUIGroup;
	}

	private UIGroup createPlayerSetting(float x,float y, String title, int playerIndex) {
		UIGroup playerSettingUIGroup = new UIGroup(x, y);
		//Title
		Text titleText = new Text(0.0f, 0.0f, FONT_FREDOKA_ONE, title, 0.05f);
		playerSettingUIGroup.addChild(titleText);


		/**
		 * Color Carousel
		 */
		UIGroup colorCarouselUIGroup = new UIGroup(0.0f, -0.2f);

		//skin
		Image colorImage = new Image(0.0f, 0.0f, 0.2f, 0.2f, logic.getHexSkin(playerIndex));
		colorCarouselUIGroup.addChild(colorImage);

		//left arrow
		ButtonCallback leftClicked = (args) -> {
			carouselLeft(colorImage, playerIndex);
		};
		RectButton leftCarouselArrow = new RectButton(-0.25f, 0.0f, 0.08f, 0.08f, TextureLibrary.LEFT_CAROUSEL_ARROW.getTexture(),
				FONT_ROBOTO, "", 0.05f, leftClicked, null, null);
		colorCarouselUIGroup.addChild(leftCarouselArrow);

		//right arrow
		ButtonCallback rightClicked = (args) -> {
			carouselRight(colorImage, playerIndex);
		};
		RectButton rightCarouselArrow = new RectButton(0.25f, 0.0f, 0.08f, 0.08f, TextureLibrary.RIGHT_CAROUSEL_ARROW.getTexture(),
				FONT_ROBOTO, "", 0.05f, rightClicked, null, null);
		colorCarouselUIGroup.addChild(rightCarouselArrow);

		playerSettingUIGroup.addChild(colorCarouselUIGroup);


		//Name text field
		Text nameText = new Text(-0.2f, -0.40f, FONT_FREDOKA_ONE, PLAYER_NAME_LABEL, 0.05f);
		playerSettingUIGroup.addChild(nameText);
		TextField playerNameTextField = new TextField(0.15f, -0.40f, FONT_FREDOKA_ONE, "Click to type", 0.045f);
		logic.setPlayerName(playerIndex, playerNameTextField);
		playerSettingUIGroup.addChild(playerNameTextField);


		/**
		 * Type Carousel
		 */
		UIGroup typeCarouselUIGroup = new UIGroup(0.0f, -0.5f);

		//text
		Text typeText = new Text(0.0f, 0.0f, FONT_FREDOKA_ONE, logic.getPlayerTypeString(playerIndex), 0.04f);
		typeCarouselUIGroup.addChild(typeText);

		//left arrow
		ButtonCallback typeLeftClicked = (args) -> {
			playerTypeLeft(typeText, playerIndex);
		};
		RectButton typeLeftCarouselArrow = new RectButton(-0.32f, 0.0f, 0.06f, 0.06f, TextureLibrary.LEFT_CAROUSEL_ARROW.getTexture(),
				FONT_ROBOTO, "", 0.05f, typeLeftClicked, null, null);
		typeCarouselUIGroup.addChild(typeLeftCarouselArrow);

		//right arrow
		ButtonCallback typeRightClicked = (args) -> {
			playerTypeRight(typeText, playerIndex);
		};
		RectButton typeRightCarouselArrow = new RectButton(0.32f, 0.0f, 0.06f, 0.06f, TextureLibrary.RIGHT_CAROUSEL_ARROW.getTexture(),
				FONT_ROBOTO, "", 0.05f, typeRightClicked, null, null);
		typeCarouselUIGroup.addChild(typeRightCarouselArrow);

		playerSettingUIGroup.addChild(typeCarouselUIGroup);


		return playerSettingUIGroup;
	}

	public void carouselLeft(Image colorImage, int playerIndex) {
		logic.previousSkin(playerIndex);
		colorImage.setTexture(logic.getHexSkin(logic.getPlayerSkinIndex(playerIndex)));
	}

	public void carouselRight(Image colorImage, int playerIndex) {
		logic.nextSkin(playerIndex);
		colorImage.setTexture(logic.getHexSkin(logic.getPlayerSkinIndex(playerIndex)));
	}

	public void playerTypeLeft(Text typeText, int playerIndex) {
		logic.previousPlayerType(playerIndex);
		typeText.setText(logic.getPlayerTypeString(playerIndex));
	}

	public void playerTypeRight(Text typeText, int playerIndex) {
		logic.nextPlayerType(playerIndex);
		typeText.setText(logic.getPlayerTypeString(playerIndex));
	}

	private void startGame() {
		GameCustomisation gameCustomization = new GameCustomisation(
				logic.getPlayerName(0),
				logic.getPlayerName(1),
				new PlayerSkin(logic.getPlayerSkin(0), Colour.White), // TODO: Colour to be chosen by player in gui
				new PlayerSkin(logic.getPlayerSkin(1), Colour.White),	// TODO: Colour to be chosen by player in gui
				60, //TODO: Time restriction should be chosen by player
				logic.getSwapRule());

		//TODO Should be chosen by player
		int boardSize = 11;
		Board b = new Board(boardSize);
		Player p1 = new UserPlayer(TileColour.BLUE);
		Player p2 = new UserPlayer(TileColour.RED);
		
		SceneDirector.changeScene(
				new GameplayScene(
						new GameLogic(b, p1, p2),
						gameCustomization));
	}

	public boolean getSwapRule() {
		return logic.getSwapRule();
	}

	public Texture getHexSkin(int playerIndex) {
		return logic.getHexSkin(logic.getPlayerSkinIndex(playerIndex));
	}
}

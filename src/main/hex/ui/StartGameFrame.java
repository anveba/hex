package main.hex.ui;

import main.engine.*;
import main.engine.font.BitmapFont;
import main.engine.graphics.*;
import main.engine.ui.*;
import main.hex.Board;
import main.hex.GameLogic;
import main.hex.scene.GameplayScene;
import main.hex.scene.SceneDirector;

import java.util.ArrayList;

public class StartGameFrame extends Frame {

	//FONTS:
	BitmapFont FONT_ROBOTO = ResourceManager.getInstance().loadFont("fonts/roboto.ttf");
	BitmapFont FONT_FREDOKA_ONE = ResourceManager.getInstance().loadFont("fonts/fredoka-one.one-regular.ttf");

	//TEXTURES:
	Texture TEXTURE_LOGO = ResourceManager.getInstance().loadTexture("textures/gui/misc/logo.png");
	Texture TEXTURE_BLUE_TILE = ResourceManager.getInstance().loadTexture("textures/board/blue_tile.png");
	Texture TEXTURE_RED_TILE = ResourceManager.getInstance().loadTexture("textures/board/red_tile.png");
	Texture TEXTURE_YELLOW_TILE = ResourceManager.getInstance().loadTexture("textures/board/yellow_tile.png");
	Texture TEXTURE_BACKGROUND_BOX = ResourceManager.getInstance().loadTexture("textures/gui/BoxesBanners/Box_Square.png");
	Texture TEXTURE_BACKGROUND_BANNER = ResourceManager.getInstance().loadTexture("textures/gui/BoxesBanners/Banner_Grey.png");
	Texture TEXTURE_ORANGE_BUTTON = ResourceManager.getInstance().loadTexture("textures/gui/ButtonsText/ButtonText_Large_Orange_Round.png");
	Texture TEXTURE_GREEN_YES_BUTTON = ResourceManager.getInstance().loadTexture("textures/gui/ButtonsText/PremadeButtons_YesGreen.png");
	Texture TEXTURE_ORANGE_NO_BUTTON = ResourceManager.getInstance().loadTexture("textures/gui/ButtonsText/PremadeButtons_No.png");
	Texture TEXTURE_LEFT_CAROUSEL_ARROW = ResourceManager.getInstance().loadTexture("textures/gui/Sliders/ScrollSlider_Blank_Arrow_Left.png");
	Texture TEXTURE_RIGHT_CAROUSEL_ARROW = ResourceManager.getInstance().loadTexture("textures/gui/Sliders/ScrollSlider_Blank_Arrow_Right.png");

	//STRINGS
	private String FRAME_TITLE = "Game Settings";
	private String START_GAME_BTN_TEXT = "Start Game";
	private String GAME_SETTINGS_SIZE_TEXT = "Board Size:";
	private String GAME_SETTINGS_TIME_TEXT = "Time Limit:";
	private String GAME_SETTINGS_SWAP_RULE_TEXT = "Enable Swap Rule:";
	private String PLAYER1_TITLE = "Player 1";
	private String PLAYER2_TITLE = "Player 2";
	private String PLAYER_NAME_LABEL = "Name:";
	private String PLAYER_TYPE_LABEL = "Player Type:";

	//LOGIC
	private boolean swapRule = false;

	private ArrayList<Texture> hexSkins = new ArrayList<>();
	private String[] playerNames = new String[2];
	private int[] playerTextureIndex = new int[2];



	//Currently an example UI class
	public StartGameFrame() {

		hexSkins.add(TEXTURE_BLUE_TILE);
		hexSkins.add(TEXTURE_RED_TILE);
		hexSkins.add(TEXTURE_YELLOW_TILE);
		playerTextureIndex[1] = 1;


		//Main menu extends Frame, so it has a UI element as a root
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
		RectButton startGameBtn = new RectButton(0.0f, -0.8f, 0.25f, 0.25f, TEXTURE_ORANGE_BUTTON,
				0.5f, 0.2f, 0, 0, TEXTURE_ORANGE_BUTTON.width(), TEXTURE_ORANGE_BUTTON.height(),
				FONT_FREDOKA_ONE, START_GAME_BTN_TEXT, 0.055f, startGameBtnClicked, null, null);
		settingsMenu.addChild(startGameBtn);
	}

	private UIGroup createBackground() {
		UIGroup backgroundUIGroup = new UIGroup(0.0f, 0.0f);
		Image background = new Image(0, -0.075f, 1.8f, 1.8f, TEXTURE_BACKGROUND_BOX, 0, 0, TEXTURE_BACKGROUND_BOX.width(), TEXTURE_BACKGROUND_BOX.height());
		backgroundUIGroup.addChild(background);

		//Background + Banner + Banner Text
		UIGroup banner = new UIGroup(0, 0.78f);
		Image bannerBackground = new Image(0, 0.0f, 1f, 0.4f, TEXTURE_BACKGROUND_BANNER, 0, 0, TEXTURE_BACKGROUND_BANNER.width(), TEXTURE_BACKGROUND_BANNER.height());
		Text bannerText = new Text(00.0f, 0.02f, FONT_FREDOKA_ONE, FRAME_TITLE, 0.1f);
		banner.addChild(bannerBackground);
		banner.addChild(bannerText);

		backgroundUIGroup.addChild(banner);

		return backgroundUIGroup;
	}

	private UIGroup createGameSettings() {
		UIGroup gameSettings = new UIGroup(-0.6f, 0.45f);

		//Board Size
		Text SizeText = new Text(00.0f, 0.00f, FONT_FREDOKA_ONE, GAME_SETTINGS_SIZE_TEXT, 0.05f);
		gameSettings.addChild(SizeText);

		//Time Limit
		Text TimeText = new Text(00.0f, -0.1f, FONT_FREDOKA_ONE, GAME_SETTINGS_TIME_TEXT, 0.05f);
		gameSettings.addChild(TimeText);

		//Swap Rule
		UIGroup swapRuleUIGroup = new UIGroup(0.11f, -0.2f);

		Text SwapRuleText = new Text(0.0f, 0.0f, FONT_FREDOKA_ONE, GAME_SETTINGS_SWAP_RULE_TEXT, 0.05f);
		swapRuleUIGroup.addChild(SwapRuleText);

		RectButton swapRuleBtn = new RectButton(0.45f, -0.01f, 0.25f, 0.1f, TEXTURE_ORANGE_NO_BUTTON,
				0.25f, 0.1f, 0, 0, TEXTURE_ORANGE_NO_BUTTON.width(), TEXTURE_ORANGE_NO_BUTTON.height(),
				FONT_ROBOTO, "", 0.05f, null, null, null);

		ButtonCallback swapruleBtnClicked = (args) -> {
			if(!swapRule) {
				swapRuleBtn.updateImageTexture(TEXTURE_GREEN_YES_BUTTON);
				swapRule = true;
				System.out.println("Swaprule enabled!");
			}
			else {
				swapRuleBtn.updateImageTexture(TEXTURE_ORANGE_NO_BUTTON);
				swapRule = false;
				System.out.println("Swaprule disabled!");
			}
		};
		swapRuleBtn.setClickCallback(swapruleBtnClicked);
		swapRuleUIGroup.addChild(swapRuleBtn);


		gameSettings.addChild(swapRuleUIGroup);

		return gameSettings;
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
		Image colorImage = new Image(0.0f, 0.0f, 0.2f, 0.2f, hexSkins.get(playerIndex), 0, 0, hexSkins.get(playerIndex).width(), hexSkins.get(playerIndex).height());
		colorCarouselUIGroup.addChild(colorImage);

		//left arrow
		RectButton leftCarouselArrow = new RectButton(-0.25f, 0.0f, 0.2f, 0.2f, TEXTURE_LEFT_CAROUSEL_ARROW,
				0.08f, 0.08f, 0, 0, TEXTURE_LEFT_CAROUSEL_ARROW.width(), TEXTURE_LEFT_CAROUSEL_ARROW.height(),
				FONT_ROBOTO, "", 0.05f, null, null, null);
		ButtonCallback leftClicked = (args) -> {
			System.out.println("left clicked");
			carouselLeft(colorImage, playerIndex);
		};
		leftCarouselArrow.setClickCallback(leftClicked);
		colorCarouselUIGroup.addChild(leftCarouselArrow);

		//right arrow
		RectButton rightCarouselArrow = new RectButton(0.25f, 0.0f, 0.2f, 0.2f, TEXTURE_RIGHT_CAROUSEL_ARROW,
				0.08f, 0.08f, 0, 0, TEXTURE_RIGHT_CAROUSEL_ARROW.width(), TEXTURE_RIGHT_CAROUSEL_ARROW.height(),
				FONT_ROBOTO, "", 0.05f, null, null, null);
		colorCarouselUIGroup.addChild(rightCarouselArrow);
		ButtonCallback rightClicked = (args) -> {
			System.out.println("right clicked");
			carouselRight(colorImage, playerIndex);
			};
		rightCarouselArrow.setClickCallback(rightClicked);
		playerSettingUIGroup.addChild(colorCarouselUIGroup);


		//Name text field
		Text nameText = new Text(-0.2f, -0.40f, FONT_FREDOKA_ONE, PLAYER_NAME_LABEL, 0.05f);
		playerSettingUIGroup.addChild(nameText);

		//Player Type
		Text AIToggle = new Text(-0.1f, -0.50f, FONT_FREDOKA_ONE, PLAYER_TYPE_LABEL, 0.05f);
		playerSettingUIGroup.addChild(AIToggle);

		return playerSettingUIGroup;
	}

	public void carouselLeft(Image colorImage, int playerIndex) {
		int i = playerTextureIndex[playerIndex];
		i = (i >= hexSkins.size() - 1) ?  0 : i + 1;

		colorImage.setTexture(hexSkins.get(i));
		playerTextureIndex[playerIndex] = i;
	}

	public void carouselRight(Image colorImage, int playerIndex) {
		int i = playerTextureIndex[playerIndex];
		i = (i == 0) ?  hexSkins.size() - 1 : i - 1;

		colorImage.setTexture(hexSkins.get(i));
		playerTextureIndex[playerIndex] = i;
	}

	private void startGame() {
		int boardSize = 5; //TODO Should be chosen by player
		SceneDirector.changeScene(new GameplayScene(new GameLogic(new Board(boardSize))));
	}
    
}

package main.hex.ui;

import main.engine.ResourceManager;
import main.engine.font.BitmapFont;
import main.engine.ui.*;
import main.hex.resources.TextureLibrary;
import main.hex.scene.GameplayScene;
import main.hex.scene.SceneDirector;
import main.hex.scene.TitleScene;
import main.hex.serialisation.GameSession;
import main.hex.serialisation.HexFileSystem;

/**
 * Main menu of the game HEX which is the first thing shown to the user.
 *
 * @Author Oliver Siggaard - s204450
 */

public class MainMenuFrame extends Frame {
    // Font:
    private BitmapFont FONT_FREDOKA_ONE = ResourceManager.getInstance().loadFont("fonts/fredoka-one.one-regular.ttf");
    private float buttonFontSize = 0.10f;

    public MainMenuFrame() {
        UIGroup root = new UIGroup(0.0f, 0.0f);
        setRoot(root);

        initializeMainMenuFrame(root);
    }

    public void initializeMainMenuFrame(UIGroup root) {
        UIGroup mainMenuView = new UIGroup(0.0f, 0.0f);
        root.addChild(mainMenuView);

        mainMenuView.addChild(new ToggleSwitch(-0.7f, 0.0f, 0.15f, 0.15f * 0.45f, false,
                null, null, null, null));
        mainMenuView.addChild(createLogoView());
        mainMenuView.addChild(createButtonMenuView());
    }

    private UIGroup createLogoView() {
        UIGroup logoView = new UIGroup(0.0f, 0.65f);

        Image hexLogo = new Image(0.0f, 0.0f, 1.2f, 1.2f * 0.45f, TextureLibrary.HEX_LOGO.getTexture());
        logoView.addChild(hexLogo);

        return logoView;
    }

    private UIGroup createButtonMenuView() {
        UIGroup buttonMenuView = new UIGroup(0.0f, 0.0f);

        buttonMenuView.addChild(createMenuButton(0, "New Game", (args) -> newGameClicked()));
        buttonMenuView.addChild(createMenuButton(1, "Load Game", (args) -> loadGameClicked()));
        buttonMenuView.addChild(createMenuButton(2, "Options", (args) -> optionsClicked()));
        buttonMenuView.addChild(createMenuButton(3, "Quit", (args) -> quitClicked()));

        return buttonMenuView;
    }

    private RectButton createMenuButton(int buttonNumber, String buttonText, ButtonCallback onclickCallback) {
        RectButton menuButton = new RectButton(0.0f, 0.15f - 0.28f * buttonNumber, 0.85f, 0.85f * 0.24f,
                TextureLibrary.BUTTON_TEXT_LARGE_SQUARE.getTexture(), FONT_FREDOKA_ONE,
                buttonText, buttonFontSize, onclickCallback, null, null);

        return menuButton;
    }

    private void newGameClicked() {
        SceneDirector.changeScene(new TitleScene());
    }

    private void loadGameClicked() {
    	System.out.println("Game Loaded!");
		
		GameSession session = HexFileSystem.getInstance().loadGame();
		
		SceneDirector.changeScene(
				new GameplayScene(
						session.gameLogic,
						session.customisation));
    }

    private void optionsClicked() {
        FrameStack.getInstance().push(new OptionsFrame());
    }

    private void quitClicked() {
        System.out.println("Quit button clicked - MainMenuFrame");
    }
}
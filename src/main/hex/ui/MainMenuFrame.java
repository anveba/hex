package main.hex.ui;

import main.engine.ResourceManager;
import main.engine.font.BitmapFont;
import main.engine.graphics.Colour;
import main.engine.ui.*;
import main.engine.ui.animation.*;
import main.engine.ui.animation.easing.CubicInOut;
import main.engine.ui.callback.ButtonCallback;
import main.hex.Game;
import main.hex.resources.TextureLibrary;
import main.hex.scene.GameplayScene;
import main.hex.scene.SceneDirector;
import main.hex.scene.TitleScene;
import main.hex.serialisation.GameSession;
import main.hex.serialisation.HexFileSystem;

/**
 * Main menu of the game HEX, which is the first thing shown to the user.
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
        
        AnimationSequence animSeq = new AnimationSequence();
        
        buttonMenuView.addChild(createMenuButton(0, "New Game", (args) -> newGameClicked(), animSeq));
        var loadGameButton = createMenuButton(1, "Load Game", (args) -> loadGameClicked(), animSeq);
        if (!HexFileSystem.getInstance().containsGameSave())
        	loadGameButton.disable();
        buttonMenuView.addChild(loadGameButton);
        buttonMenuView.addChild(createMenuButton(2, "Options", (args) -> optionsClicked(), animSeq));
        buttonMenuView.addChild(createMenuButton(3, "Quit", (args) -> quitClicked(), animSeq));
        
        addAnimator(new Animator(animSeq));

        return buttonMenuView;
    }

    private RectButton createMenuButton(int buttonNumber, String buttonText,
    		ButtonCallback onclickCallback, AnimationSequence animationSequence) {
    	float y = 0.15f - 0.28f * buttonNumber;
        RectButton menuButton = new RectButton(0.0f, y, 0.85f, 0.85f * 0.24f,
                TextureLibrary.BUTTON_TEXT_LARGE_SQUARE.getTexture(), FONT_FREDOKA_ONE,
                buttonText, buttonFontSize, onclickCallback, null, null);
        
        Animation anim = new Ease(menuButton, new CubicInOut(),
        		0.0f, y - 2.0f, 
        		0.0f, y,
        		1.1f);
        animationSequence.append(anim, new Wait(0.08f));

        return menuButton;
    }

    private void newGameClicked() {
        SceneDirector.changeScene(new TitleScene());
    }

    private void loadGameClicked() {
    	System.out.println("Game Loaded!");
    	
    	GameSession session;
    	try {
    		session = HexFileSystem.getInstance().loadGame();
    	} catch (Exception e) {
    		return;
    	}
		
		SceneDirector.changeScene(
				new GameplayScene(
						session.gameLogic,
						session.customisation));
    }

    private void optionsClicked() {
        FrameStack.getInstance().push(new OptionsFrame());
    }

    private void quitClicked() {
        Game.getInstance().closeWindow();
    }
}
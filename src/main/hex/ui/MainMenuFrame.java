package main.hex.ui;

import main.engine.font.BitmapFont;
import main.engine.graphics.Colour;
import main.engine.io.ResourceManager;
import main.engine.ui.*;
import main.engine.ui.animation.*;
import main.engine.ui.animation.easing.CubicInOut;
import main.engine.ui.callback.ButtonCallback;
import main.hex.Game;
import main.hex.HexException;
import main.hex.resources.TextureLibrary;
import main.hex.scene.GameplayScene;
import main.hex.scene.SceneDirector;
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
    
    private Image blackOutImage;
    private HexBackground hexBackground;
    private RectButton loadGameBtn;
    private RectButton newGameBtn;
    private UIGroup mainMenuView;

    public MainMenuFrame(HexBackground hexBackground) {
        if (hexBackground == null) {
            this.hexBackground = new HexBackground(0.0f, 0.0f, 0.05f, -0.025f, TextureLibrary.BACKGROUND_TILE_GREYSCALE.getTexture(), Colour.Background_Grey);
        } else {
            this.hexBackground = hexBackground;
        }
        UIGroup root = new UIGroup(0.0f, 0.0f);
        setRoot(root);

        initializeMainMenuFrame(root);
    }

    public void initializeMainMenuFrame(UIGroup root) {
        mainMenuView = new UIGroup(0.0f, 0.0f);
        root.addChild(mainMenuView);

        mainMenuView.addChild(hexBackground);
        mainMenuView.addChild(createLogoView());
        mainMenuView.addChild(createButtonMenuView());
        mainMenuView.addChild(createBlackOutImage());
        
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
        
        newGameBtn = createMenuButton(0, "New Game", (args) -> newGameClicked(), animSeq);
        buttonMenuView.addChild(newGameBtn);
        loadGameBtn = createMenuButton(1, "Load Game", (args) -> loadGameClicked(), animSeq);
        if (!HexFileSystem.getInstance().containsGameSave())
        	loadGameBtn.disable();
        buttonMenuView.addChild(loadGameBtn);
        var optionsBtn = createMenuButton(2, "Options", (args) -> optionsClicked(), animSeq);
        buttonMenuView.addChild(optionsBtn);
        var quitBtn = createMenuButton(3, "Quit", (args) -> quitClicked(), animSeq);
        buttonMenuView.addChild(quitBtn);
        
        final float buttonStart = -1.5f;
        animSeq = new AnimationSequence(
        		new SetPosition(newGameBtn, 0.0f, buttonStart),
        		new SetPosition(loadGameBtn, 0.0f, buttonStart),
        		new SetPosition(optionsBtn, 0.0f, buttonStart),
        		new SetPosition(quitBtn, 0.0f, buttonStart),
        		animSeq
        		); 
        
        addAnimator(new Animator(animSeq));

        return buttonMenuView;
    }

    private RectButton createMenuButton(int buttonNumber, String buttonText,
    		ButtonCallback onclickCallback, AnimationSequence animationSequence) {
        RectButton menuButton = new RectButton(0.0f, -2.0f, 0.85f, 0.85f * 0.24f,
                TextureLibrary.BUTTON_TEXT_LARGE_SQUARE.getTexture(), FONT_FREDOKA_ONE,
                buttonText, buttonFontSize, onclickCallback, null, null);
        
        float y = 0.15f - 0.28f * buttonNumber;
        Animation anim = new Ease(menuButton, new CubicInOut(),
        		0.0f, y - 2.0f, 
        		0.0f, y,
        		1.1f);
        animationSequence.append(anim, new Wait(0.08f));

        return menuButton;
    }

    private void newGameClicked() {
        mainMenuView.removeChild(hexBackground);
        FrameStack.getInstance().push(new StartGameFrame(hexBackground));
    }
    
    private Image createBlackOutImage() {
    	blackOutImage = new Image(0.0f, 0.0f, 50.0f, 2.0f, 
        		TextureLibrary.WHITE_PX.getTexture(), Colour.Black);
		blackOutImage.hide();
		return blackOutImage;
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
    
    private void loadGameClicked() {
    	newGameBtn.disable();
    	loadGameBtn.disable();
    	fadeOut(1.0f, () -> {
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
    	});
    }

    private void optionsClicked() {
        mainMenuView.removeChild(hexBackground);
        OptionsFrame optionsFrame = new OptionsFrame();
        optionsFrame.setHexBackground(hexBackground);
        FrameStack.getInstance().push(optionsFrame);
    }

    private void quitClicked() {
    	fadeOut(1.0f, () -> Game.getInstance().closeWindow());
    }
}
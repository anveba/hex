package main.hex;

import main.hex.scene.SceneDirector;
import main.hex.scene.TitleScene;
import main.hex.ui.GameplayFrame;

import main.engine.*;
import main.engine.graphics.*;
import main.engine.input.Controls;
import main.engine.ui.FrameStack;
import main.hex.ui.StartGameFrame;

public class Game extends GameWindow {

    private Renderer2D renderer2D;
    
    private static Game instance;
    public static Game getInstance() {
    	return instance;
    }

    public static void main(String[] args) {
        new Game().startGame("Hex", 800, 800);
    }
    private Game() {
    	if (instance != null) {
    		throw new HexException("Several instances of the game was created");
    	}
    	instance = this;
    }
    
    @Override
    protected void begin() {
    	setupGraphics();
    	setupUserInterface();
    	
    	SceneDirector.changeScene(new TitleScene());
    }
    
    private void setupUserInterface() {
    	
        getControlsListener().addOnButtonReleaseCallback(Controls.LEFT_MOUSE, (args) -> {
            FrameStack.getInstance().clickAt(
            		getControlsListener().getCursorX(),
            		getControlsListener().getCursorY()
            		);
        });
        
        getControlsListener().addOnCursorMoveCallback((x, y) -> {
            FrameStack.getInstance().hoverAt(x, y);
        });
        
        getControlsListener().addTextInputCallback((ch) -> {
            FrameStack.getInstance().processTextInput(ch);
        });
        
        getControlsListener().addOnAnyReleaseCallback((args) -> {
            FrameStack.getInstance().processControlsInput(args);
        });
    }
    
    private void setupGraphics() {
    	renderer2D = new Renderer2D(this);
        setClearColor(0.4f, 0.2f, 0.5f);
    }

    @Override
    protected void update(TimeRecord elapsed) {
    	SceneDirector.updateCurrentScene(elapsed);
    	FrameStack.getInstance().update(elapsed);
    }

    @Override
    protected void draw() {
        clear();
        FrameStack.getInstance().draw(renderer2D);
        SceneDirector.drawCurrentScene2D(renderer2D);
    }
}
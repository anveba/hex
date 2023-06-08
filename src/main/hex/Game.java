package main.hex;

import main.engine.input.InputType;
import main.hex.scene.MainMenuScene;
import main.hex.scene.SceneDirector;
import main.hex.scene.TitleScene;
import main.hex.ui.GameplayFrame;

import main.engine.*;
import main.engine.graphics.*;
import main.engine.input.Controls;
import main.engine.input.ControlsArgs;
import main.engine.ui.FrameStack;
import main.hex.ui.StartGameFrame;

public class Game extends GameWindow {

    private Renderer2D renderer2D;
    private Renderer3D renderer3D;
    private Camera camera;
    
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
    	
    	SceneDirector.changeScene(new MainMenuScene());
    }
    
    private void setupUserInterface() {
        
        getControlsListener().addOnCursorMoveCallback((x, y) -> {
            FrameStack.getInstance().hoverAt(x, y);
        });
        
        getControlsListener().addTextInputCallback((ch) -> {
            FrameStack.getInstance().processTextInput(ch);
        });

    }
    
    private void setupGraphics() {
    	renderer2D = new Renderer2D(this);
    	camera = new Camera(0.1f, 100.0f, (float)Math.PI / 4.0f);
    	renderer3D = new Renderer3D(camera, this);

        // Purple colour: 0.4f, 0.2f, 0.5f
        setClearColor(0.15f, 0.15f, 0.16f);
    }

    @Override
    protected void update(TimeRecord elapsed) {
    	SceneDirector.updateCurrentScene(elapsed);
    	FrameStack.getInstance().update(elapsed);
    	
    	if (getControlsListener().isPressed(Controls.LEFT_MOUSE)) {
    		FrameStack.getInstance().pressAt(
            		getControlsListener().getCursorX(),
            		getControlsListener().getCursorY()
            		);
    	}
        if (getControlsListener().isReleased(Controls.LEFT_MOUSE)) {
            FrameStack.getInstance().clickReleaseAt(
                    getControlsListener().getCursorX(),
                    getControlsListener().getCursorY()
            );
        }
    	
    	for (Controls c : getControlsListener().currentlyReleased())
    		FrameStack.getInstance().processControlsInput(new ControlsArgs(c));

    	getControlsListener().flush();
    }

    @Override
    protected void draw() {
        clear();
        if (Preferences.getInstance().is3DEnabled())
        	SceneDirector.drawCurrentScene3D(renderer3D);
        else
        	SceneDirector.drawCurrentScene2D(renderer2D);
        
        FrameStack.getInstance().draw(renderer2D);
    }
    
    public Camera getCamera() {
    	return camera;
    }
}
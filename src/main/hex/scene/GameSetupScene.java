package main.hex.scene;

import main.engine.TimeRecord;
import main.engine.graphics.Renderer2D;
import main.engine.graphics.Renderer3D;
import main.engine.ui.FrameStack;
import main.hex.ui.MainMenuFrame;

/**
 * This scene allows for switching between all the frames related to the game setup.
 */

public class GameSetupScene extends Scene {

	public GameSetupScene() {
		
	}
	
	@Override
	protected void begin() {
		FrameStack.getInstance().push(new MainMenuFrame(null));
	}
	
	@Override
	protected void end() {
		
	}

	@Override
	protected void update(TimeRecord time) {
		
	}

	@Override
	protected void draw2D(Renderer2D renderer) {
		
	}

	@Override
	protected void draw3D(Renderer3D renderer) {
		
	}
}
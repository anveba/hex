package main.hex.scene;

import main.engine.TimeRecord;
import main.engine.graphics.Renderer2D;
import main.engine.graphics.Renderer3D;
import main.engine.ui.Frame;
import main.engine.ui.FrameStack;
import main.hex.ui.MainMenuFrame;
import main.hex.ui.StartGameFrame;

public class TitleScene extends Scene {

	public TitleScene() {
		
	}
	
	@Override
	protected void begin() {
		setupUserInterface();
	}
	
	private void setupUserInterface() {
		FrameStack.getInstance().clear();
		FrameStack.getInstance().push(new StartGameFrame());
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

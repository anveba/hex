package main.hex.scene;

import main.engine.TimeRecord;
import main.engine.graphics.Renderer2D;
import main.engine.graphics.Renderer3D;
import main.engine.ui.FrameStack;
import main.hex.ui.StartGameFrame;

public class TitleScene extends Scene {

	public TitleScene() {
		
	}
	
	@Override
	public void begin() {
		setupUserInterface();
	}
	
	private void setupUserInterface() {
		FrameStack.getInstance().clear();
		FrameStack.getInstance().push(new StartGameFrame());
	}
	
	@Override
	public void end() {
		
	}

	@Override
	public void update(TimeRecord time) {
		
	}

	@Override
	public void draw2D(Renderer2D renderer) {
		
	}

	@Override
	public void draw3D(Renderer3D renderer) {
		
	}

}

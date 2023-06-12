package main.hex.scene;

import main.engine.TimeRecord;
import main.engine.graphics.Renderer2D;
import main.engine.graphics.Renderer3D;
import main.engine.ui.Frame;

public abstract class Scene {

	protected abstract void begin();
	
	protected abstract void end();

	protected abstract void update(TimeRecord time);
	
	protected abstract void draw2D(Renderer2D renderer);
	
	protected abstract void draw3D(Renderer3D renderer);

	public abstract void changeFrame(Frame newFrame);
}

package main.hex.scene;

import main.engine.TimeRecord;
import main.engine.graphics.Renderer2D;

public abstract class Scene {

	public abstract void begin();
	
	public abstract void end();
	
	public abstract void update(TimeRecord time);
	
	public abstract void draw2D(Renderer2D renderer);
	
}

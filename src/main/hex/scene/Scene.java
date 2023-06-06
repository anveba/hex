package main.hex.scene;

import main.engine.TimeRecord;
import main.engine.graphics.Renderer2D;
import main.engine.graphics.Renderer3D;

public abstract class Scene {

	private boolean updatesPaused = false;

	public abstract void begin();
	
	public abstract void end();
	
	public void update(TimeRecord time) {
		if (updatesPaused) return;

		updateScene(time);
	}

	protected abstract void updateScene(TimeRecord time);
	
	public abstract void draw2D(Renderer2D renderer);
	
	public abstract void draw3D(Renderer3D renderer);

	public boolean isUpdatesPaused() {
		return updatesPaused;
	}

	public void pauseUpdates() {
		updatesPaused = true;
	}

	public void resumeUpdates() {
		updatesPaused = false;
	}
}

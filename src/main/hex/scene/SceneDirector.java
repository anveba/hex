package main.hex.scene;

import main.engine.TimeRecord;
import main.engine.graphics.Renderer2D;
import main.engine.graphics.Renderer3D;
import main.hex.HexException;

public class SceneDirector {
	
	private static SceneDirector instance;
	private static SceneDirector getInstance() {
		if (instance == null)
			instance = new SceneDirector();
		return instance;
	}
	
	private Scene currentScene;
	private boolean isPaused;
	
	private SceneDirector() {
		isPaused = false;
	}
	
	public static void changeScene(Scene scene) {
		if (scene == null)
			throw new HexException("Scene was null");
		if (getInstance().currentScene != null)
			getInstance().currentScene.end();
		getInstance().currentScene = scene;
		getInstance().currentScene.begin();
	}
	
	public static void updateCurrentScene(TimeRecord time) {
		if (isPaused())
			return;
		if (getInstance().currentScene == null)
			throw new HexException("No current scene active");
		getInstance().currentScene.update(time);
	}
	
	public static void drawCurrentScene2D(Renderer2D renderer) {
		if (getInstance().currentScene == null)
			throw new HexException("No current scene active");
		getInstance().currentScene.draw2D(renderer);
	}
	
	public static void drawCurrentScene3D(Renderer3D renderer) {
		if (getInstance().currentScene == null)
			throw new HexException("No current scene active");
		getInstance().currentScene.draw3D(renderer);
	}
	
	public static Scene currentScene() {
		return getInstance().currentScene;
	}
	
	public static boolean isPaused() {
		return getInstance().isPaused;
	}
	
	public static void pause() {
		getInstance().isPaused = true;
	}
	
	public static void resume() {
		getInstance().isPaused = false;
	}
}

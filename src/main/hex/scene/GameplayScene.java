package main.hex.scene;

import main.engine.TimeRecord;
import main.engine.graphics.Colour;
import main.engine.graphics.Renderer2D;
import main.engine.graphics.Renderer3D;
import main.engine.math.Vector3;
import main.engine.ui.FrameStack;
import main.hex.*;
import main.hex.graphics.Meshes;
import main.hex.ui.GameplayFrame;

public class GameplayScene extends Scene {

	private GameLogic logic;
	private GameCustomisation gameCustomization;
	private CameraController camController;
	
	public GameplayScene(GameLogic logic, GameCustomisation gameCustomization) {
		if (logic == null || gameCustomization == null)
			throw new HexException("null was given");
		this.logic = logic;
		this.gameCustomization = gameCustomization;
		camController = new CameraController(Game.getInstance().getCamera());
	}
	
	@Override
	public void begin() {
		setUpUserInterface();
		startGameplay();
		setUpCamera();
	}
	
	private void setUpUserInterface() {
		FrameStack.getInstance().clear();
		FrameStack.getInstance().push(new GameplayFrame(gameCustomization));
	}
	
    private void startGameplay() {
    	
    	logic.setPlayerWinCallback(this::onPlayerWin);
		logic.setSwapRuleState(gameCustomization.getSwapRule());
    	logic.start();
    }
    
    private void setUpCamera() {
    	Game.getInstance().getCamera().setX(0.0f);
    	Game.getInstance().getCamera().setY(8.0f);
    	Game.getInstance().getCamera().setZ(8.0f);
    	Game.getInstance().getCamera().setPitch((float)Math.PI / -4.0f);
    	Game.getInstance().getCamera().setYaw(0.0f);
    	Game.getInstance().getCamera().setRoll(0.0f);
    	Game.getInstance().getCamera().setPlanes(0.1f, 100.0f);
    }
    
    private void onPlayerWin(Player p) {
    	//TODO currently a temporary method body
    	System.out.println(p.getColour() + " has won!");
    	SceneDirector.changeScene(new TitleScene());
    }
	
	@Override
	public void end() {
	}

	@Override
	public void update(TimeRecord time) {
		logic.update(time);
		camController.update(time);
	}

	@Override
	public void draw2D(Renderer2D renderer) {
		logic.getBoard().draw2D(renderer, gameCustomization);
	}
	
	@Override
	public void draw3D(Renderer3D renderer) {
		logic.getBoard().draw3D(renderer, gameCustomization);
	}

}

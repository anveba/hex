package main.hex.scene;

import main.engine.ResourceManager;
import main.engine.TimeRecord;
import main.engine.graphics.Cubemap;
import main.engine.graphics.Renderer2D;
import main.engine.graphics.Renderer3D;
import main.engine.ui.FrameStack;
import main.hex.*;
import main.hex.player.Player;
import main.hex.serialisation.GameState;
import main.hex.serialisation.GameStateSerialiser;
import main.hex.ui.GameplayFrame;
import main.hex.ui.MainMenuFrame;

public class GameplayScene extends Scene {

	private GameLogic gameLogic;
	private GameCustomisation gameCustomization;
	private CameraController camController;
	private Cubemap skybox;
	
	public GameplayScene(GameLogic gameLogic, GameCustomisation gameCustomisation) {
		if (gameLogic == null || gameCustomisation == null)
			throw new HexException("null was given");
		this.gameLogic = gameLogic;
		this.gameCustomization = gameCustomisation;
		camController = new CameraController(Game.getInstance().getCamera());
		skybox = ResourceManager.getInstance().loadCubemap("cubemaps/indoors");
	}
	
	@Override
	protected void begin() {
		setUpUserInterface();
		startGameplay();
		setUpCamera();
	}
	
	private void setUpUserInterface() {
		FrameStack.getInstance().clear();
		FrameStack.getInstance().push(new GameplayFrame(gameCustomization, gameLogic));
	}
	
    private void startGameplay() {
    	
    	gameLogic.setPlayerWinCallback(this::onPlayerWin);
    	if (!gameLogic.gameHasStarted())
    		gameLogic.start();
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
	protected void end() {
		
	}

	@Override
	protected void update(TimeRecord time) {

		gameLogic.update(time);
		gameCustomization.setSwapped(gameLogic.coloursSwapped());
		camController.update(time);

		gameLogic.getPlayer1().getTimer().update(time);
		gameLogic.getPlayer2().getTimer().update(time);
	}

	@Override
	protected void draw2D(Renderer2D renderer) {
		gameLogic.getBoard().draw2D(renderer, gameCustomization);
	}
	
	@Override
	protected void draw3D(Renderer3D renderer) {
		renderer.setSkybox(skybox, 2.0f);
		gameLogic.getBoard().draw3D(renderer, gameCustomization);
		renderer.drawSkybox();
	}

}

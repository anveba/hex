package main.hex.scene;

import main.engine.TimeRecord;
import main.engine.graphics.Renderer2D;
import main.engine.ui.FrameStack;
import main.hex.*;
import main.hex.ui.GameFrame;

public class GameplayScene extends Scene {

	private GameLogic logic;
	
	public GameplayScene(GameLogic logic) {
		if (logic == null)
			throw new HexException("null was given");
		this.logic = logic;
	}
	
	@Override
	public void begin() {
		setupUserInterface();
		startGameplay();
	}
	
	private void setupUserInterface() {
		FrameStack.getInstance().clear();
		FrameStack.getInstance().push(new GameFrame());
	}
	
    private void startGameplay() {
    	logic.setPlayerWinCallback(this::onPlayerWin);
        logic.setupControlsCallback(Game.getInstance().getControlsListener());
    }
    
    private void onPlayerWin(Player p) {
    	//TODO currently a temporary method body
    	System.out.println(p.getPlayerColour() + " has won!");
    	SceneDirector.changeScene(new TitleScene());
    }
	
	@Override
	public void end() {
		logic.removeControlsCallback(Game.getInstance().getControlsListener());
	}

	@Override
	public void update(TimeRecord time) {
		
	}

	@Override
	public void draw2D(Renderer2D renderer) {
		logic.getBoard().draw(renderer);
	}

}

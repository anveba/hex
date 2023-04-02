package main.hex.scene;

import main.engine.TimeRecord;
import main.engine.graphics.Colour;
import main.engine.graphics.Renderer2D;
import main.engine.ui.FrameStack;
import main.hex.*;
import main.hex.ui.GameplayFrame;
import main.hex.ui.GameCustomization;

public class GameplayScene extends Scene {

	private GameLogic logic;
	private GameCustomization gameCustomization;
	
	public GameplayScene(GameLogic logic, GameCustomization gameCustomization) {
		if (logic == null || gameCustomization == null)
			throw new HexException("null was given");
		this.logic = logic;
		this.gameCustomization = gameCustomization;
	}
	
	@Override
	public void begin() {
		setupUserInterface();
		startGameplay();
	}
	
	private void setupUserInterface() {
		FrameStack.getInstance().clear();
		FrameStack.getInstance().push(new GameplayFrame(gameCustomization));
	}
	
    private void startGameplay() {
    	logic.setPlayerWinCallback(this::onPlayerWin);
    	logic.start();
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
	}

	@Override
	public void draw2D(Renderer2D renderer) {
		logic.getBoard().draw(renderer, gameCustomization);
	}

}

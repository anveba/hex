package main.hex;

import main.hex.ui.GameFrame;

import main.engine.*;
import main.engine.graphics.*;
import main.engine.input.Controls;
import main.engine.ui.FrameStack;
import main.hex.ui.StartGameFrame;

public class Game extends GameWindow {

    private Board board = null;
    private Renderer2D renderer2D;
    
    private static Game instance;
    public static Game getInstance() {
    	return instance;
    }

    public static void main(String[] args) {
        new Game().startGame("Hex", 800, 800);
    }
    private Game() {
    	if (instance != null) {
    		throw new HexException("Several instances of the game was created");
    	}
    	instance = this;
    }
    
    @Override
    protected void begin() {
    	setupGraphics();
    	setupUserInterface();
    	
    	startGameplay();
    }
    
    private void setupUserInterface() {
        FrameStack.getInstance().push(new GameFrame(board));
        FrameStack.getInstance().push(new StartGameFrame());
        
        getControlsListener().addOnReleaseCallback(Controls.LEFT_MOUSE, (args) -> {
            FrameStack.getInstance().clickAt(
            		getControlsListener().getCursorX(),
            		getControlsListener().getCursorY()
            		);
         });
        
        getControlsListener().addOnCursorMoveCallback((x, y) -> {
            FrameStack.getInstance().hoverAt(x, y);
         });
    }
    
    private void setupGraphics() {
    	renderer2D = new Renderer2D(this);
        setClearColor(0.4f, 0.2f, 0.5f);
    }
    
    private void startGameplay() {
    	board = new Board(5);
        GameLogic gameLogic = new GameLogic(board, this::onPlayerWin);
        gameLogic.setupControlsCallback(getControlsListener());
    }
    
    private void onPlayerWin(Player p) {
    	System.out.println(p.getPlayerColour() + " has won!");
    }

    @Override
    protected void update(TimeRecord elapsed) {
    }

    @Override
    protected void draw() {
        clear();
        if(board != null && FrameStack.getInstance().peek() instanceof GameFrame)
        	board.draw(renderer2D);

        FrameStack.getInstance().draw(renderer2D);

    }
}
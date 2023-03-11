package main.hex;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import main.engine.*;
import main.engine.font.*;
import main.engine.graphics.*;
import main.engine.input.Controls;
import main.engine.ui.FrameStack;
import main.hex.ui.MainMenu;

import java.io.File;
import java.io.IOException;
import java.nio.*;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.lwjgl.stb.*;

public class Game extends GameWindow {

    private Board board;
    private Renderer2D renderer2D;
    
    private static Game instance;
    public static Game getInstance() {
    	return instance;
    }

    private Game() {
    	if (instance != null) {
    		throw new HexException("Several instances of the game was created");
    	}
    	instance = this;
    }

    @Override
    protected void begin() {
    	renderer2D = new Renderer2D(this);
        board = new Board(11);
        
        FrameStack.getInstance().push(new MainMenu());

        GameLogic gameLogic = new GameLogic(board);
        gameLogic.setupControlsCallback(getControlsListener());

        setClearColor(0.4f, 0.2f, 0.5f);
        
        getControlsListener().addOnReleaseCallback(Controls.ESCAPE, (args) -> {
           closeWindow(); 
        });
        
        getControlsListener().addOnReleaseCallback(Controls.LEFT_MOUSE, (args) -> {
            FrameStack.getInstance().clickAt(
            		getControlsListener().getCursorX(),
            		getControlsListener().getCursorY()
            		);
         });
    }

    @Override
    protected void update(TimeRecord elapsed) {
    }

    @Override
    protected void draw() {
    	
    	if (!board.hasLoadedDrawingResources())
    		board.loadDrawingResources();

        clear();

        board.draw(renderer2D);
        
        FrameStack.getInstance().draw(renderer2D);
    }

    public static void main(String[] args) {
        new Game().startGame("Hex", 800, 800);
    }

}
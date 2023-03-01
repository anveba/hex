package hex;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

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
import engine.*;
import engine.font.BitmapFont;
import engine.graphics.Renderer2D;
import engine.graphics.Texture;
import engine.input.Controls;

public class Entry extends GameWindow {

    private Board board;
    private Renderer2D r2D;
    private BitmapFont font;

    public Entry() {

    }

    @Override
    protected void begin() {
        r2D = new Renderer2D(this);
        font = new BitmapFont("res/fonts/roboto.ttf", 64.0f);
        board = new Board(11);

        setClearColor(0.4f, 0.2f, 0.5f);
        
        getControlsListener().addOnReleaseCallback(Controls.ESCAPE, (args) -> {
           closeWindow(); 
        });
    }

    @Override
    protected void update(TimeRecord elapsed) {
    }

    @Override
    protected void draw() {

        clear();

        board.draw(r2D);
        
        r2D.drawString(font, "Hello world!", 
                getControlsListener().getCursorX(), 
                getControlsListener().getCursorY(), 
                0.1f);
        
        
    }

    public static void main(String[] args) {
        new Entry().startGame("Hex", 800, 800);
    }

}
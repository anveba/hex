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

public class Entry extends GameWindow {

    private Texture tex;
    private Renderer2D r2D;
    private BitmapFont font;

    public Entry() {

    }

    @Override
    protected void begin() {
        tex = new Texture("res/textures/test_input.jpg");
        r2D = new Renderer2D(this);
        font = new BitmapFont("res/fonts/roboto.ttf", 64.0f);

        setClearColor(0.4f, 0.2f, 0.5f);
    }

    @Override
    protected void update(TimeRecord elapsed) {

    }

    @Override
    protected void draw() {

        clear();

        r2D.drawString(font, "Hello world!", 0.0f, 0.5f, 0.1f);
        r2D.draw(tex, 0.0f, 0.0f, 0.4f, 0.4f, 0, 0, tex.width(), tex.height());

    }

    public static void main(String[] args) {
        new Entry().startGame("Hex", 800, 800);
    }

}
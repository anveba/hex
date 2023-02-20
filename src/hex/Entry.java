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
    private TimeRecord elapsed;

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
        this.elapsed = elapsed;
    }

    @Override
    protected void draw() {

        clear();

        r2D.drawString(font, "Hello world!", 0.0f, 0.0f, 0.1f);
        
        final int drawCount = 100;
        
        for (int i = 0; i < drawCount; i++) {
            float f = elapsed.totalSeconds() + (float)i / drawCount * 2.0f * (float)Math.PI;
            int mod = (i % 100);
            r2D.draw(tex, 
                    (float)Math.cos(f * 0.01241f * mod + 1.432f * mod), 
                    (float)Math.sin(f * 0.05234f * mod + 40.4327f * mod), 
                    (float)Math.cos(f * 0.01444f * mod + 342.0432f * mod) / 5.0f, 
                    (float)Math.sin(f * 0.00452f * mod + 24.3421f * mod) / 5.0f, 
                    0, 0, tex.width(), tex.height());
        }
    }

    public static void main(String[] args) {
        new Entry().startGame("Hex", 800, 800);
    }

}
package main.engine.graphics;

import static org.lwjgl.opengl.GL33.*;

import java.nio.ByteBuffer;

import org.lwjgl.stb.STBImage;

import main.engine.*;

public class Texture {

    private int handle;
    private int width, height;

    public Texture(String path) {

        int[] x = new int[1];
        int[] y = new int[1];
        int[] channels = new int[1];

        STBImage.stbi_set_flip_vertically_on_load(true);

        ByteBuffer data = STBImage.stbi_load(path, x, y, channels, 4);
        if (data == null)
            throw new EngineException("texture load failed: " + STBImage.stbi_failure_reason());

        handle = glGenTextures();
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, handle);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, x[0], y[0], 0, GL_RGBA, GL_UNSIGNED_BYTE, data);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        width = x[0];
        height = y[0];
    }
    
    protected Texture(int width, int height) {
    	if (width < 1 || height < 1) {
    		throw new EngineException("Illegal texture dimensions");
    	}
    	handle = glGenTextures();
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, handle);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, 0);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    }

    @Override
    protected void finalize() {
    	if (handle != 0)
    		glDeleteTextures(handle);
    }

    public void use(int slot) {
        // TODO check bounds
        glActiveTexture(GL_TEXTURE0 + slot);
        glBindTexture(GL_TEXTURE_2D, handle);
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }
}

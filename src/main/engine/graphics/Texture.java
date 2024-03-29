package main.engine.graphics;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL12.GL_TEXTURE_WRAP_R;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP;
import static org.lwjgl.opengl.GL33.*;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.file.Files;

import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImage;
import static org.lwjgl.stb.STBImage.*;
import org.lwjgl.system.*;
import static org.lwjgl.system.MemoryStack.*;

import static main.engine.Utility.*;

import main.engine.*;

/**
 * Abstracts a texture (image) that is located in GPU memory. 
 * @author Andreas - s214971
 *
 */
public class Texture {

    private int handle;
    private int width, height;

    public Texture(byte[] fileData) {
        
        ByteBuffer buffer = byteArrayToByteBuffer(fileData);
        
        try (MemoryStack stack = stackPush()) {
        	IntBuffer x = stack.mallocInt(1);
        	IntBuffer y = stack.mallocInt(1);
        	IntBuffer channels = stack.mallocInt(1);

        	STBImage.stbi_set_flip_vertically_on_load(true);
        	
        	if (!stbi_info_from_memory(buffer, x, y, channels))
        		throw new EngineException("Failed to load image information: " + stbi_failure_reason());
        	
        	ByteBuffer data = STBImage.stbi_load_from_memory(buffer, x, y, channels, 4);
        	if (data == null)
        		throw new EngineException("Texture load failed: " + STBImage.stbi_failure_reason());
        	width = x.get(0);
        	height = y.get(0);
        	
        	handle = glGenTextures();
            glActiveTexture(GL_TEXTURE0);
            glBindTexture(GL_TEXTURE_2D, handle);
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
            STBImage.stbi_image_free(data);
        }
        
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
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
        if (slot < 0 || slot >= slotCount())
        	throw new EngineException("No texture slot " + slot);
        glActiveTexture(GL_TEXTURE0 + slot);
        glBindTexture(GL_TEXTURE_2D, handle);
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }
    
    public static int slotCount() {
    	return 8;
    }
}

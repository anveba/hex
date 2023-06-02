package main.engine.graphics;

import static main.engine.Utility.byteArrayToByteBuffer;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.system.MemoryStack.stackPush;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import main.engine.EngineException;

public class Cubemap {
	
	private int handle;
	
    public Cubemap(byte[][] fileData) {
    	
    	if (fileData.length != 6)
    		throw new EngineException("Six files were not supplied");
        
    	handle = glGenTextures();
    	glBindTexture(GL_TEXTURE_CUBE_MAP, handle);
    	
    	glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_R, GL_CLAMP_TO_EDGE);
    	
    	glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    	
    	for (int i = 0; i < fileData.length; i++) {
    		
	        ByteBuffer buffer = byteArrayToByteBuffer(fileData[i]);
	        
	        try (MemoryStack stack = stackPush()) {
	        	IntBuffer x = stack.mallocInt(1);
	        	IntBuffer y = stack.mallocInt(1);
	        	IntBuffer channels = stack.mallocInt(1);
	
	        	STBImage.stbi_set_flip_vertically_on_load(false);
	        	
	        	if (!stbi_info_from_memory(buffer, x, y, channels))
	        		throw new EngineException("Failed to load image information: " + stbi_failure_reason());
	        	
	        	ByteBuffer data = STBImage.stbi_load_from_memory(buffer, x, y, channels, 4);
	        	if (data == null)
	        		throw new EngineException("Cubemap load failed: " + STBImage.stbi_failure_reason());
	        	int width = x.get(0);
	        	int height = y.get(0);
	        	
	            glTexImage2D(GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, GL_RGB, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
	            
	            STBImage.stbi_image_free(data);
	        }
    	}
    }

    @Override
    protected void finalize() {
    	if (handle != 0)
    		glDeleteTextures(handle);
    }

    public void use(int slot) {
        if (slot < 0 || slot >= Texture.slotCount())
        	throw new EngineException("No texture slot " + slot);
        glActiveTexture(GL_TEXTURE0 + slot);
        glBindTexture(GL_TEXTURE_CUBE_MAP, handle);
    }
}

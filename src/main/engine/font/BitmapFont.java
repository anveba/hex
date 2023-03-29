package main.engine.font;

import java.io.File;
import java.io.IOException;
import java.nio.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBTTBakedChar;
import org.lwjgl.stb.STBTruetype;
import org.lwjgl.system.MemoryStack;

import main.engine.*;
import main.engine.graphics.Texture;

import static main.engine.Utility.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.system.MemoryStack.stackPush;

public class BitmapFont {

    private FontCharacterData[] chars;
    private int bitmapWidth, bitmapHeight;
    private float charHeight;
    private int textureHandle;
    private int characterCount, characterOffset;

    public BitmapFont(byte[] fileData, float heightInPx) {
    	characterOffset = 32;
    	characterCount = 96;
    	bitmapWidth = 1024;
    	bitmapHeight = 1024;
    	charHeight = heightInPx;
    	
    	var d = loadFontData(
    			fileData, heightInPx,
    			bitmapWidth, bitmapHeight, 
    			characterOffset, characterCount);
    	chars = d.charData;
        
    	textureHandle = generateGLTexture(d.bitmap, bitmapWidth, bitmapHeight);
    }
    
    private static FontData loadFontData(
    		byte[] fileData, float heightInPx,
    		int bitmapWidth, int bitmapHeight,
    		int characterOffset, int characterCount) {
    	
    	ByteBuffer buffer = byteArrayToByteBuffer(fileData);

        var bitmap = BufferUtils.createByteBuffer(bitmapWidth * bitmapHeight);

        var charDataBuffer = STBTTBakedChar.malloc(characterCount);

        int res = STBTruetype.stbtt_BakeFontBitmap(buffer, heightInPx, bitmap, 
        		bitmapWidth, bitmapHeight, characterOffset,
                charDataBuffer);
        if (res <= 0)
        	throw new EngineException("Could not fit font on texture");

        var verticallyFlippedBitmap = verticallyFlipBitmap(bitmap, bitmapWidth, bitmapHeight);
        
        var d = new FontData();
        d.bitmap = verticallyFlippedBitmap;
        d.charData = convertSTBTTCharBufferToCharacterData(charDataBuffer, 
        		characterOffset, characterCount, bitmapHeight);
        
        return d;
    }

	private static ByteBuffer verticallyFlipBitmap(ByteBuffer bitmap, int bitmapWidth, int bitmapHeight) {
		var verticallyFlippedBitmap = BufferUtils.createByteBuffer(bitmapWidth * bitmapHeight);
        for (int y = 0; y < bitmapHeight; y++) {
            for (int x = 0; x < bitmapWidth; x++) {
                verticallyFlippedBitmap.put(x + (bitmapHeight - 1 - y) * bitmapWidth, bitmap.get(x + y * bitmapWidth));
            }
        }
		return verticallyFlippedBitmap;
	}
    
    private static FontCharacterData[] convertSTBTTCharBufferToCharacterData(
    		STBTTBakedChar.Buffer charDataBuffer,
    		int characterOffset, int characterCount, int bitmapHeight) {
    	
    	FontCharacterData[] chars = new FontCharacterData[characterCount];
        for (int i = 0; i < characterCount; i++) {
            var d = charDataBuffer.get();
            chars[i] = new FontCharacterData(d.x0(), d.x1(), bitmapHeight - 1 - d.y0(), bitmapHeight - 1 - d.y1(),
                    d.xadvance(), d.xoff(), -d.yoff());
        }
        charDataBuffer.free();
        return chars;
    }
    
    private static int generateGLTexture(ByteBuffer bitmap, int width, int height) {
    	int handle = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, handle);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RED, GL_UNSIGNED_BYTE,
                bitmap);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        return handle;
    }

    @Override
    protected void finalize() {
    	if (textureHandle != 0)
    		glDeleteTextures(textureHandle);
    }

    public void useTexture(int slot) {
        if (slot < 0 || slot >= Texture.slotCount())
        	throw new EngineException("No texture slot " + slot);
        glActiveTexture(GL_TEXTURE0 + slot);
        glBindTexture(GL_TEXTURE_2D, textureHandle);
    }

    public int bitmapWidth() {
        return bitmapWidth;
    }

    public int bitmapHeight() {
        return bitmapHeight;
    }

    public float charHeight() {
        return charHeight;
    }

    public FontCharacterData getCharacterData(char c) {
        if (c < characterOffset || c >= characterOffset + characterCount)
        	throw new EngineException("Character <" + c + "> (code " + (int)c + ") not contained or loaded in font");
        return chars[c - characterOffset];
    }

    public Vector2 measureString(String str) {
        float width = 0.0f, height = 0.0f;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            var d = getCharacterData(c);
            width += d.advance();
            float dh = d.y0() - d.y1();
            height = height > dh ? height : dh;
        }
        return new Vector2(width, height);
    }
}

package engine.font;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;

import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBTTBakedChar;
import org.lwjgl.stb.STBTruetype;

import static org.lwjgl.opengl.GL33.*;
import engine.*;

public class BitmapFont {

    private FontCharacterData[] chars;
    private int bitmapWidth, bitmapHeight;
    private float charHeight;
    private int textureHandle;

    public BitmapFont(String path, float heightInPx) {
        File fontFile = new File(path);
        byte[] fontRaw;
        try {
            fontRaw = Files.readAllBytes(fontFile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            throw new EngineException(e);
        }
        ByteBuffer fontBuffer = BufferUtils.createByteBuffer(fontRaw.length);
        fontBuffer.put(fontRaw);
        fontBuffer.flip();

        bitmapWidth = 512;
        bitmapHeight = 512;
        charHeight = heightInPx;
        var bitmap = BufferUtils.createByteBuffer(bitmapWidth * bitmapHeight);

        int numChars = 96;
        var charDataBuffer = STBTTBakedChar.malloc(numChars);

        int res = STBTruetype.stbtt_BakeFontBitmap(fontBuffer, charHeight, bitmap, bitmapWidth, bitmapHeight, 32,
                charDataBuffer);
        // TODO check res

        var verticallyFlippedBitmap = BufferUtils.createByteBuffer(bitmapWidth * bitmapHeight);
        for (int y = 0; y < bitmapHeight; y++) {
            for (int x = 0; x < bitmapWidth; x++) {
                verticallyFlippedBitmap.put(x + (bitmapHeight - 1 - y) * bitmapWidth, bitmap.get(x + y * bitmapWidth));
            }
        }

        chars = new FontCharacterData[numChars];
        for (int i = 0; i < numChars; i++) {
            var d = charDataBuffer.get();
            chars[i] = new FontCharacterData(d.x0(), d.x1(), bitmapHeight - 1 - d.y0(), bitmapHeight - 1 - d.y1(),
                    d.xadvance(), d.xoff(), -d.yoff());
        }
        charDataBuffer.free();

        textureHandle = glGenTextures();
        glActiveTexture(GL_TEXTURE1);
        glBindTexture(GL_TEXTURE_2D, textureHandle);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, bitmapWidth, bitmapHeight, 0, GL_ALPHA, GL_UNSIGNED_BYTE,
                verticallyFlippedBitmap);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    }

    @Override
    protected void finalize() {
        glDeleteTextures(textureHandle);
    }

    public void useTexture(int slot) {
        // TODO check bounds
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
        // TODO check bounds
        return chars[c - 32];
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

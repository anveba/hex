package main.engine;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;

import org.lwjgl.BufferUtils;

public class Utility {

    public static boolean floatEquals(float f1, float f2) {
        return Math.abs(f1 - f2) < 0.001f;
    }
    
    public static ByteBuffer pathToByteBuffer(String path) {
    	File file = new File(path);
        byte[] fileAsByteArray;
        try {
        	fileAsByteArray = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            throw new EngineException(e);
        }
        ByteBuffer buffer = BufferUtils.createByteBuffer(fileAsByteArray.length);
        buffer.put(fileAsByteArray);
        buffer.flip();
        return buffer;
    }
}

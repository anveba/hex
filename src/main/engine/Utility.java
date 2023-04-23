package main.engine;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;

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
        return byteArrayToByteBuffer(fileAsByteArray);
    }
    
    public static ByteBuffer byteArrayToByteBuffer(byte[] arr) {
        ByteBuffer buffer = BufferUtils.createByteBuffer(arr.length);
        buffer.put(arr);
        buffer.flip();
        return buffer;
    }
    
    private static String readFileAsString(String stringPath) {
    	Path path = Path.of(stringPath);
        if (!Files.exists(path))
        	throw new EngineException("Path does not correspond to a file");
        try {
            return Files.readString(path);
        } catch (IOException ex) {
            throw new EngineException(ex);
        }
    }
    
    public static float clamp(float val, float lower, float upper) {
    	if (lower > upper) {
    		float temp = upper;
    		upper = lower;
    		lower = temp;
    	}
    	if (val < lower)
    		return lower;
    	if (val > upper)
    		return upper;
    	return val;
    }
}

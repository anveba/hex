package main.engine;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;

import org.lwjgl.BufferUtils;

/**
 * A static class containing useful pure functions.
 * @author Andreas - s214971
 *
 */
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
    
    public static float lerp(float val, float lower, float upper) {
    	if (val < 0.0f || val > 1.0f)
    		throw new EngineException("Value wasn't between 0 and 1");
    	
    	float delta = upper - lower;
    	return lower + delta * val;
    }

    public static String getFormattedTime(double time) {
        int minutes = (int)(time / 60.0);
        int seconds = (int)(time % 60.0);
        return String.format("%02d:%02d", minutes, seconds);
    }
}

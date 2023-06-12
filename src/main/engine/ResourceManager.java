package main.engine;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

import main.engine.font.BitmapFont;
import main.engine.graphics.*;
import main.engine.sound.Sound;

/**
 * This class handles all IO pertaining to resources such as textures and shaders.
 * It abstracts the file system and handles OS-specific file system quirks. It 
 * will cache already-loaded resources.
 * 
 * This class is a singleton.
 * 
 * @author Andreas
 *
 */

public class ResourceManager {
    
    private static ResourceManager instance = null;
    
    public static ResourceManager getInstance() {
        if (instance == null)
            instance = new ResourceManager();
        return instance;
    }
    
    private Map<String, Texture> textures;
    private Map<String, Shader> shaders;
    private Map<String, BitmapFont> fonts;
    private Map<String, Cubemap> cubemaps;
    private Map<String, Sound> sounds;
    
    private ResourceManager() {
        textures = new HashMap<>();
        shaders = new HashMap<>();
        fonts = new HashMap<>();
        cubemaps = new HashMap<>();
        sounds = new HashMap<>();
    }
    
    public Texture loadTexture(String path) {
        if (textures.containsKey(path))
            return textures.get(path);
        Texture t = new Texture(readBytesFromRelativePath(path));
        textures.put(path, t);
        return t;
    }
    
    public Cubemap loadCubemap(String basePath) {
    	if (cubemaps.containsKey(basePath))
    		return cubemaps.get(basePath);
    	
    	byte[][] fileData = new byte[6][];
    	for(int i = 0; i < 6; i++) 
    		fileData[i] = readBytesFromRelativePath(basePath + "/" + getCubemapFaceName(i) + ".hdr");
    	
    	Cubemap cubemap = new Cubemap(fileData);
    	cubemaps.put(basePath, cubemap);
    	return cubemap;
    }
    
    private String getCubemapFaceName(int index) {
    	String face = "";
		face += index % 2 == 0 ? "p" : "n";
		face += index < 2 ? "x" : index < 4 ? "y" : "z";
		return face;
    }
    
    public Shader loadShader(String path) {
        if (shaders.containsKey(path))
            return shaders.get(path);
        Shader s = new Shader(
        		new String(readBytesFromRelativePath(path + ".vert"), StandardCharsets.UTF_8), 
        		new String(readBytesFromRelativePath(path + ".frag"), StandardCharsets.UTF_8));
        shaders.put(path, s);
        return s;
    }
    
    public BitmapFont loadFont(String path) {
        if (fonts.containsKey(path))
            return fonts.get(path);
        BitmapFont f = new BitmapFont(readBytesFromRelativePath(path), 128.0f);
        fonts.put(path, f);
        return f;
    }
    
    public Sound loadSound(String path) {
    	if (sounds.containsKey(path))
            return sounds.get(path);
        Sound s = Sound.load(getAbsolutePathOf(path));
        sounds.put(path, s);
        return s;
    }
    
    public String getAbsolutePathOf(String path) {
    	path = "res/" + path;
        var cl = getClass().getClassLoader();
        var res = cl.getResource(path);
        if (res == null)
        	throw new EngineException("Resource not found: " + path);
    	return res.toString();
    }
    
    public byte[] readBytesFromRelativePath(String path) {
        path = "res/" + path;
        var cl = getClass().getClassLoader();
        var r = cl.getResourceAsStream(path);
        if (r == null)
        	throw new EngineException("Resource not found: " + path);
        try {
			return r.readAllBytes();
		} catch (IOException e) {
			throw new EngineException("Couldn't read input stream");
		}
    }
}

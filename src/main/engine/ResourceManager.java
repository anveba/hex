package main.engine;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import main.engine.font.BitmapFont;
import main.engine.graphics.*;

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
    
    private ResourceManager() {
        textures = new HashMap<>();
        shaders = new HashMap<>();
        fonts = new HashMap<>();
    }
    
    public Texture loadTexture(String path) {
        if (textures.containsKey(path))
            return textures.get(path);
        Texture t = new Texture(getBytesFromRelativePath(path));
        textures.put(path, t);
        return t;
    }
    
    public Shader loadShader(String path) {
        if (shaders.containsKey(path))
            return shaders.get(path);
        Shader s = new Shader(
        		new String(getBytesFromRelativePath(path + ".vert"), StandardCharsets.UTF_8), 
        		new String(getBytesFromRelativePath(path + ".frag"), StandardCharsets.UTF_8));
        shaders.put(path, s);
        return s;
    }
    
    public BitmapFont loadFont(String path) {
        if (fonts.containsKey(path))
            return fonts.get(path);
        BitmapFont f = new BitmapFont(getBytesFromRelativePath(path), 128.0f);
        fonts.put(path, f);
        return f;
    }
    
    private byte[] getBytesFromRelativePath(String path) {
        path = "res/" + path;
        var cl = getClass().getClassLoader();
        var r = cl.getResourceAsStream(path);
        if (r == null)
        	throw new EngineException("Resource not found");
        try {
			return r.readAllBytes();
		} catch (IOException e) {
			throw new EngineException("Couldn't read input stream");
		}
    }
    
}

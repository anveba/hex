package main.engine;

import java.net.URISyntaxException;
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
    
    Map<String, Texture> textures;
    Map<String, Shader> shaders;
    Map<String, BitmapFont> fonts;
    
    private ResourceManager() {
        textures = new HashMap<>();
        shaders = new HashMap<>();
        fonts = new HashMap<>();
    }
    
    public Texture loadTexture(String path) {
        path = "res/" + path;
        if (textures.containsKey(path))
            return textures.get(path);
        var cl = ResourceManager.class.getClassLoader();
        var r = cl.getResource(path);
        Texture t;
        try {
            t = new Texture(fixPath(r.toURI().getRawPath()));
        } catch (URISyntaxException e) {
			throw new EngineException("URI to URL error");
		}


        textures.put(path, t);
        return t;
    }
    
    public Shader loadShader(String path) {
        path = "res/" + path;
        if (shaders.containsKey(path))
            return shaders.get(path);
        var cl = ResourceManager.class.getClassLoader();
        var vert = cl.getResource(path + ".vert");
        var frag = cl.getResource(path + ".frag");
        Shader s;
		try {
			s = new Shader(fixPath(vert.toURI().getRawPath()), fixPath(frag.toURI().getRawPath()));
		} catch (URISyntaxException e) {
			throw new EngineException("URI to URL error");
		}
        shaders.put(path, s);
        return s;
    }
    
    public BitmapFont loadFont(String path) {
        path = "res/" + path;
        if (fonts.containsKey(path))
            return fonts.get(path);
        var cl = getClass().getClassLoader();
        var r = cl.getResource(path);
        BitmapFont f;
        try {
        	f = new BitmapFont(fixPath(r.toURI().getRawPath()), 32.0f);
		} catch (URISyntaxException e) {
			throw new EngineException("URI to URL error");
		}
        fonts.put(path, f);
        return f;
    }

    public String fixPath(String s){
        if(s.contains(":")){
            return s.substring(s.indexOf(":")+1);
        }
        return s;
    }
    
}

package engine;
import java.util.*;
import engine.graphics.*;

public class ResourceManager {
    
    private static ResourceManager instance = null;
    public static ResourceManager getInstance() {
        if (instance == null)
            instance = new ResourceManager();
        return instance;
    }
    
    Map<String, Texture> textures;
    
    public ResourceManager() {
        textures = new HashMap<>();
    }
    
    public Texture loadTexture(String path) {
        path = "res/" + path;
        if (textures.containsKey(path))
            return textures.get(path);
        Texture t = new Texture(path);
        textures.put(path, t);
        return t;
    }
    
}

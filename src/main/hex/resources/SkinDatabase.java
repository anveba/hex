package main.hex.resources;

import main.engine.ResourceManager;
import main.engine.graphics.Texture;
import main.hex.HexException;

import java.util.HashMap;
import java.util.Map;

/**
 * Maps a skin ID to its corresponding texture.
 * 
 * Singleton.
 * 
 * @author andreas
 *
 */
public class SkinDatabase {

	private static SkinDatabase instance;
	
	public static SkinDatabase getInstance() {
		if (instance == null)
			instance = new SkinDatabase();
		return instance;
	}
	
	public final static int defaultTextureId = 0;
	public final static int zebraTextureId = 1;
	public final static int largeBubbleTextureId = 2;
	public final static int smallBubbleTextureId = 3;
	public final static int abstractTextureId = 4;
	public final static int duckTextureId = 5;
	
	private Map<Integer, String> idToTexturePath;
	
	private SkinDatabase() {
		idToTexturePath = new HashMap<>();
		
		idToTexturePath.put(defaultTextureId, "textures/board/white_tile.png");
		idToTexturePath.put(zebraTextureId, "textures/board/zebra_tile.png");
		idToTexturePath.put(largeBubbleTextureId, "textures/board/large_bubble_tile.png");
		idToTexturePath.put(smallBubbleTextureId, "textures/board/small_bubble_tile.png");
		idToTexturePath.put(abstractTextureId, "textures/board/abstract_tile.png");
		idToTexturePath.put(duckTextureId, "textures/board/duck_tile.png");
	}

	public Texture getTextureFromId(int id) {
		if (!idToTexturePath.containsKey(id))
			throw new HexException("Skin ID not recognised");
		return ResourceManager.getInstance().loadTexture(idToTexturePath.get(id));
	}
}

package main.hex.resources;

import java.util.*;

import main.engine.ResourceManager;
import main.engine.graphics.Texture;
import main.hex.HexException;

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
	
	public final static int defaultSkinId = 0;
	public final static int zebraSkinId = 1;
	public final static int duckSkinId = 2;
	public final static int bubbleSkinId = 3;
	public final static int abstractSkinId = 4;
	public final static int smallBubbleSkinId = 5;
	
	private Map<Integer, String> idToTexturePath;
	
	private SkinDatabase() {
		idToTexturePath = new HashMap<>();
		
		idToTexturePath.put(defaultSkinId, "textures/board/white_tile.png");
		idToTexturePath.put(zebraSkinId, "textures/board/zebra_tile.png");
		idToTexturePath.put(duckSkinId, "textures/board/duck_tile.png");
		idToTexturePath.put(bubbleSkinId, "textures/board/large_bubble_tile.png");
		idToTexturePath.put(abstractSkinId, "textures/board/abstract_tile.png");
		idToTexturePath.put(smallBubbleSkinId, "textures/board/small_bubble_tile.png");
	}

	public Texture getTextureFromId(int id) {
		if (!idToTexturePath.containsKey(id))
			throw new HexException("Skin ID not recognised");
		return ResourceManager.getInstance().loadTexture(idToTexturePath.get(id));
	}
}

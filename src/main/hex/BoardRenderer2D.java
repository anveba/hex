package main.hex;

import main.engine.Point2;
import main.engine.ResourceManager;
import main.engine.Vector2;
import main.engine.graphics.Colour;
import main.engine.graphics.Renderer2D;
import main.engine.graphics.Texture;

public class BoardRenderer2D {

    private boolean hasLoadedResources;
    private Texture whiteTileTexture;
    
    public static final float tileSize = 0.08f;

    public BoardRenderer2D() {
        hasLoadedResources = false;
    }
    
    public Point2 screenToTile(float screenX, float screenY, float size) {
    	float tileX = screenX / tileSize;
    	float tileY = screenY / tileSize;
    	tileY = tileY / (1.1547005f * 0.75f) - 0.5f + (float)size / 2.0f;
    	tileX = tileX + 0.5f * tileY - 0.25f + (float)size / 4.0f;
    	return new Point2((int)(tileX + 0.5f), (int)(tileY + 0.5f));
    }
    
    public Vector2 tileToScreen(int tileX, int tileY, float size) {
        float screenX = ((float)tileX - (float)size / 4.0f + 0.25f) - 0.5f * tileY;
        float screenY = ((float)tileY - (float)size / 2.0f + 0.5f) * 1.1547005f * 0.75f;
        screenX *= tileSize;
        screenY *= tileSize;
        return new Vector2(screenX, screenY);
    }

    public void draw(Renderer2D renderer, Board board) {
    	if (!hasLoadedResources)
    		loadResources();
    	
    	float screenSpaceCursorX = Game.getInstance().getControlsListener().getCursorX();
    	float screenSpaceCursorY = Game.getInstance().getControlsListener().getCursorY();
    	Point2 tileSpaceCursorPosition = screenToTile(screenSpaceCursorX, screenSpaceCursorY, board.size());
        for (int x = 0; x < board.size(); x++) {
            for (int y = 0; y < board.size(); y++) {

                Tile t = board.getTileAtPosition(x, y);

                Tile.Colour tileColour = t.getColour();
                Colour drawColour;
                if (tileColour == Tile.Colour.BLUE)
                	drawColour = Colour.Blue;
                else if (tileColour == Tile.Colour.RED)
                	drawColour = Colour.Red;
                else if (tileSpaceCursorPosition.getX() == x &&
            			tileSpaceCursorPosition.getY() == y)
                	drawColour = Colour.LightGrey;
                else
                	drawColour = Colour.White;

            	Vector2 screenPos = tileToScreen(x, y, board.size());
                renderer.drawSprite(whiteTileTexture, 
                		screenPos.getX(), screenPos.getY(), (tileSize), (tileSize * 1.1547005f),
                        0, 0, whiteTileTexture.width(), whiteTileTexture.height(), drawColour);
            }
        }
    }

	public void loadResources() {
        whiteTileTexture = ResourceManager.getInstance()
                .loadTexture("textures/board/white_tile.png");
        hasLoadedResources = true;
	}
	
	public float getTileSize() {
		return tileSize;
	}
}

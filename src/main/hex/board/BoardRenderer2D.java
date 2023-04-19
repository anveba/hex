package main.hex.board;

import main.engine.Point2;
import main.engine.ResourceManager;
import main.engine.graphics.Colour;
import main.engine.graphics.Renderer2D;
import main.engine.graphics.Texture;
import main.engine.math.Vector2;
import main.hex.Game;
import main.hex.ui.GameCustomisation;

public class BoardRenderer2D {

    private boolean hasLoadedResources;
    private Texture whiteTileTexture;
    
    public static final float tileSize = 0.08f;

    public BoardRenderer2D() {
        hasLoadedResources = false;
    }
    
    public Point2 screenToTile(float screenX, float screenY, int size) {
    	float tileX = screenX / tileSize;
    	float tileY = screenY / tileSize;
    	tileY = tileY / (1.1547005f * 0.75f) - 0.5f + (float)size / 2.0f;
    	tileX = tileX + 0.5f * tileY - 0.25f + (float)size / 4.0f;
    	return new Point2((int)(tileX + 0.5f), (int)(tileY + 0.5f));
    }
    
    public Vector2 tileToScreen(int tileX, int tileY, int size) {
        float screenX = ((float)tileX - (float)size / 4.0f + 0.25f) - 0.5f * tileY;
        float screenY = ((float)tileY - (float)size / 2.0f + 0.5f) * 1.1547005f * 0.75f;
        screenX *= tileSize;
        screenY *= tileSize;
        return new Vector2(screenX, screenY);
    }

    public void draw(Renderer2D renderer, Board board, GameCustomisation gameCustomisation) {
    	if (!hasLoadedResources)
    		loadResources();
    	
    	float screenSpaceCursorX = Game.getInstance().getControlsListener().getCursorX();
    	float screenSpaceCursorY = Game.getInstance().getControlsListener().getCursorY();
    	Point2 tileSpaceCursorPosition = screenToTile(screenSpaceCursorX, screenSpaceCursorY, board.size());
        for (int x = 0; x < board.size(); x++) {
            for (int y = 0; y < board.size(); y++) {

                Tile t = board.getTileAtPosition(x, y);

                TileColour tileColour = t.getColour();
                Colour drawColour;
                if (tileColour == TileColour.BLUE)
                	drawColour = gameCustomisation.getPlayer1Skin().getPlayerColour();
                else if (tileColour == TileColour.RED)
                	drawColour = gameCustomisation.getPlayer2Skin().getPlayerColour();
                else if (tileSpaceCursorPosition.getX() == x &&
            			tileSpaceCursorPosition.getY() == y)
                	drawColour = Colour.LightGrey;
                else
                	drawColour = Colour.White;

            	Vector2 screenPos = tileToScreen(x, y, board.size());

                if (tileColour == TileColour.BLUE) {
                    renderer.drawSprite(gameCustomisation.getPlayer1Skin().getPlayerTexture(),
                            screenPos.getX(), screenPos.getY(), (tileSize), (tileSize * 1.1547005f), 0, 0,
                            gameCustomisation.getPlayer1Skin().getPlayerTexture().width(),
                            gameCustomisation.getPlayer1Skin().getPlayerTexture().height(),
                            gameCustomisation.getPlayer1Skin().getPlayerColour());
                } else if (tileColour == TileColour.RED) {
                    renderer.drawSprite(gameCustomisation.getPlayer2Skin().getPlayerTexture(),
                            screenPos.getX(), screenPos.getY(), (tileSize), (tileSize * 1.1547005f), 0, 0,
                            gameCustomisation.getPlayer1Skin().getPlayerTexture().width(),
                            gameCustomisation.getPlayer1Skin().getPlayerTexture().height(),
                            gameCustomisation.getPlayer2Skin().getPlayerColour());
                } else {
                    renderer.drawSprite(whiteTileTexture,
                            screenPos.getX(), screenPos.getY(), (tileSize), (tileSize * 1.1547005f),
                            0, 0, whiteTileTexture.width(), whiteTileTexture.height(), drawColour);
                }
            }
        }
    }

	private void loadResources() {
        whiteTileTexture = ResourceManager.getInstance()
                .loadTexture("textures/board/light_grey_tile.png");
        hasLoadedResources = true;
	}
	
	public float getTileSize() {
		return tileSize;
	}
}
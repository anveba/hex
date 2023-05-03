package main.hex.board;

import main.engine.Point2;
import main.engine.ResourceManager;
import main.engine.graphics.Colour;
import main.engine.graphics.Renderer2D;
import main.engine.graphics.Texture;
import main.engine.math.Vector2;
import main.hex.Game;
import main.hex.GameCustomisation;
import main.hex.resources.TextureLibrary;

public class BoardRenderer2D {

    private boolean hasLoadedResources;
    private Texture whiteTileTexture;
    private Texture borderTexture;

    public static final float tileSize = 0.08f;
    public static final float tileHeight = tileSize * 1.1547005f;

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

    private void drawBorder(int x, int y, Renderer2D renderer, Board board, GameCustomisation gameCustomisation) {

        Colour p1Col = gameCustomisation.getPlayer1Skin().getTint();
        Colour p2Col = gameCustomisation.getPlayer2Skin().getTint();

        if (y == 0) { // Bottom border
            /*
            if (x == board.size() - 1) { // Bottom right corner
                Vector2 bottomRightCornerScreenPos = tileToScreen(board.size(), -1, board.size());
                renderer.drawSprite(gameCustomisation.getPlayer2Skin(), bottomRightCornerScreenPos.getX(),
                        bottomRightCornerScreenPos.getY(), tileSize, tileSize * 1.1547005f);
            }
             */
            Vector2 bottomBorderScreenPos = tileToScreen(x, -1, board.size());
            renderer.drawSprite(borderTexture, p1Col, bottomBorderScreenPos.getX(),
                    bottomBorderScreenPos.getY(), tileSize, tileHeight);
        }
        if (y == board.size() - 1) { // Top border
            /*
            if (x == 0) { // Top left corner
                Vector2 topLeftCornerScreenPos = tileToScreen(-1, board.size(), board.size());
                renderer.drawSprite(gameCustomisation.getPlayer2Skin(), topLeftCornerScreenPos.getX(),
                        topLeftCornerScreenPos.getY(), tileSize, tileSize * 1.1547005f);
            }
             */
            Vector2 topBorderScreenPos = tileToScreen(x, board.size(), board.size());
            renderer.drawSprite(borderTexture, p1Col, topBorderScreenPos.getX(),
                    topBorderScreenPos.getY(), tileSize, tileHeight);
        }
        if (x == 0) { // Left border
            if (y == 0) { // Bottom left corner
                Vector2 topLeftCornerScreenPos = tileToScreen(-1, -1, board.size());
                renderer.drawSprite(borderTexture, p2Col, topLeftCornerScreenPos.getX(),
                        topLeftCornerScreenPos.getY(), tileSize, tileHeight);;
            }
            Vector2 leftBorderScreenPos = tileToScreen(-1, y, board.size());
            renderer.drawSprite(borderTexture, p2Col, leftBorderScreenPos.getX(),
                    leftBorderScreenPos.getY(), tileSize, tileHeight);
        }
        if (x == board.size() - 1) { // Right border
            if (y == board.size() - 1) { // Top right corner
                Vector2 topRightCornerScreenPos = tileToScreen(board.size(), board.size(), board.size());
                renderer.drawSprite(borderTexture, p2Col, topRightCornerScreenPos.getX(),
                        topRightCornerScreenPos.getY(), tileSize, tileHeight);
            }
            Vector2 rightBorderScreenPos = tileToScreen(board.size(), y, board.size());
            renderer.drawSprite(borderTexture, p2Col, rightBorderScreenPos.getX(),
                    rightBorderScreenPos.getY(), tileSize, tileHeight);
        }
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
                if (tileColour == TileColour.PLAYER1)
                	drawColour = gameCustomisation.getPlayer1Skin().getTint();
                else if (tileColour == TileColour.PLAYER2)
                	drawColour = gameCustomisation.getPlayer2Skin().getTint();
                else if (tileSpaceCursorPosition.getX() == x &&
            			tileSpaceCursorPosition.getY() == y)
                	drawColour = Colour.LightGrey;
                else
                	drawColour = Colour.White;

                Vector2 screenPos = tileToScreen(x, y, board.size());

                // Border
                if (x == 0 || y == 0 || x == board.size() - 1 || y == board.size() - 1) {
                    drawBorder(x, y, renderer, board, gameCustomisation);
                }

                if (tileColour == TileColour.PLAYER1) {
                    renderer.drawSprite(gameCustomisation.getPlayer1Skin().getTexture(),
                            screenPos.getX(), screenPos.getY(), (tileSize), (tileSize * 1.1547005f), 0, 0,
                            gameCustomisation.getPlayer1Skin().getTexture().width(),
                            gameCustomisation.getPlayer1Skin().getTexture().height(),
                            gameCustomisation.getPlayer1Skin().getTint());
                } else if (tileColour == TileColour.PLAYER2) {
                    renderer.drawSprite(gameCustomisation.getPlayer2Skin().getTexture(),
                            screenPos.getX(), screenPos.getY(), (tileSize), (tileSize * 1.1547005f), 0, 0,
                            gameCustomisation.getPlayer2Skin().getTexture().width(),
                            gameCustomisation.getPlayer2Skin().getTexture().height(),
                            gameCustomisation.getPlayer2Skin().getTint());
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
        borderTexture = TextureLibrary.WHITE_TILE_FULL.getTexture();
	}
	
	public float getTileSize() {
		return tileSize;
	}
}

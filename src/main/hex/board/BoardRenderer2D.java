package main.hex.board;

import main.engine.graphics.Colour;
import main.engine.graphics.Renderer2D;
import main.engine.graphics.Texture;
import main.engine.math.Point2;
import main.engine.math.Vector2;
import main.hex.Game;
import main.hex.logic.GameCustomisation;
import main.hex.resources.TextureLibrary;

public class BoardRenderer2D {

    private boolean hasLoadedResources;
    private Texture standardTileTexture, borderTexture, leftBorderCornerTexture, rightBorderCornerTexture;


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
        Colour verticalWinColour = gameCustomisation.getPlayer1Skin().getTint();
        Colour horizontalWinColour = gameCustomisation.getPlayer2Skin().getTint();
        
        if (TileColour.PLAYER2.winsByVerticalConnection()) {
        	Colour temp = verticalWinColour;
        	verticalWinColour = horizontalWinColour;
        	horizontalWinColour = temp;
        }

        // Bottom left corner
        Vector2 bottomLeftCornerScreenPos = tileToScreen(-1, -1, board.size());
        renderer.drawSprite(leftBorderCornerTexture, horizontalWinColour, (float) -Math.PI/3, bottomLeftCornerScreenPos.getX(),
                bottomLeftCornerScreenPos.getY(), tileSize, tileHeight);

        // Bottom right corner
        Vector2 topRightCornerScreenPos = tileToScreen(board.size(), board.size(), board.size());
        renderer.drawSprite(leftBorderCornerTexture, horizontalWinColour, (float) (2*Math.PI)/3, topRightCornerScreenPos.getX(),
                topRightCornerScreenPos.getY(), tileSize, tileHeight);

        // Bottom border
        if (y == 0) {
            Vector2 bottomBorderScreenPos = tileToScreen(x, -1, board.size());
            Texture texture = borderTexture;

            if (x == board.size() - 1)
                texture = leftBorderCornerTexture;

            renderer.drawSprite(texture, verticalWinColour, 0, bottomBorderScreenPos.getX(),
                    bottomBorderScreenPos.getY(), tileSize, tileHeight);
        }

        // Top border
        if (y == board.size() - 1) {
            Vector2 topBorderScreenPos = tileToScreen(x, board.size(), board.size());
            Texture texture = borderTexture;

            if (x == 0)
                texture = leftBorderCornerTexture;

            renderer.drawSprite(texture, verticalWinColour, (float) Math.PI, topBorderScreenPos.getX(),
                    topBorderScreenPos.getY(), tileSize, tileHeight);
        }

        // Left border
        if (x == 0) {
            Vector2 leftBorderScreenPos = tileToScreen(-1, y, board.size());
            Texture texture = borderTexture;

            if (y == board.size() - 1)
                texture = rightBorderCornerTexture;

            renderer.drawSprite(texture, horizontalWinColour, (float) -Math.PI / 3, leftBorderScreenPos.getX(),
                    leftBorderScreenPos.getY(), tileSize, tileHeight);
        }

        // Right border
        if (x == board.size() - 1) {
            Vector2 rightBorderScreenPos = tileToScreen(board.size(), y, board.size());
            Texture texture = borderTexture;

            if (y == 0)
                texture = rightBorderCornerTexture;

            renderer.drawSprite(texture, horizontalWinColour, (float) (2*Math.PI)/3, rightBorderScreenPos.getX(),
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
                    renderer.drawSprite(standardTileTexture,
                            screenPos.getX(), screenPos.getY(), (tileSize), (tileSize * 1.1547005f),
                            0, 0, standardTileTexture.width(), standardTileTexture.height(), drawColour);
                }
            }
        }
    }

	private void loadResources() {
        standardTileTexture = TextureLibrary.LIGHT_GREY_TILE.getTexture();
        borderTexture = TextureLibrary.BORDER.getTexture();
        leftBorderCornerTexture = TextureLibrary.LEFT_BORDER_CORNER.getTexture();
        rightBorderCornerTexture = TextureLibrary.RIGHT_BORDER_CORNER.getTexture();
        hasLoadedResources = true;
	}
	
	public float getTileSize() {
		return tileSize;
	}
}

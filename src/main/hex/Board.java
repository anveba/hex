package main.hex;

import java.util.Arrays;

import main.engine.*;
import main.engine.graphics.*;

public class Board implements Drawable2D{
	
    private Tile[][] board;
    private boolean hasLoadedResources;
    private Texture whiteTileTexture;
    private Texture redTileTexture;
    private Texture blueTileTexture;
    
    private float tileSize = 0.08f;

    public Board(int size){
        if(size <= 0){ throw new HexException("Nonpositive board size was given");}

        board = new Tile[size][size];

        for(int x = 0; x < size; x++){
            for(int y = 0; y < size; y++){
                board[x][y] = new Tile();
            }
        }
        
        hasLoadedResources = false;
    }

    public int getBoardSize(){
        return board.length;
    }

    public Tile getTileAtPosition(int x, int y){
        if(x >= getBoardSize() || y >= getBoardSize() || x < 0 || y < 0){
            throw new HexException("Out of bounds of board");
        }
        return board[x][y];
    }

    public Point2 screenToTile(float screenX, float screenY) {
    	float tileX = screenX / tileSize;
    	float tileY = screenY / tileSize;
    	tileY = tileY / (1.1547005f * 0.75f) - 0.5f + (float)getBoardSize() / 2.0f;
    	tileX = tileX + 0.5f * tileY - 0.25f + (float)getBoardSize() / 4.0f;
    	return new Point2((int)(tileX + 0.5f), (int)(tileY + 0.5f));
    }
    
    public Vector2 tileToScreen(int tileX, int tileY) {
        float screenX = ((float)tileX - (float)getBoardSize() / 4.0f + 0.25f) - 0.5f * tileY;
        float screenY = ((float)tileY - (float)getBoardSize() / 2.0f + 0.5f) * 1.1547005f * 0.75f;
        screenX *= tileSize;
        screenY *= tileSize;
        return new Vector2(screenX, screenY);
    }

    @Override
    public void draw(Renderer2D renderer) {
    	float screenSpaceCursorX = Game.getInstance().getControlsListener().getCursorX();
    	float screenSpaceCursorY = Game.getInstance().getControlsListener().getCursorY();
    	Point2 tileSpaceCursorPosition = screenToTile(screenSpaceCursorX, screenSpaceCursorY);
        for (int x = 0; x < getBoardSize(); x++) {
            for (int y = 0; y < getBoardSize(); y++) {

            	Texture drawTexture = whiteTileTexture;
            	
            	Vector2 screenPos = tileToScreen(x, y);
            	if (tileSpaceCursorPosition.getX() == x &&
            			tileSpaceCursorPosition.getY() == y)
            		drawTexture = redTileTexture;
                
                renderer.draw(drawTexture, 
                		screenPos.getX(), screenPos.getY(), (tileSize), (tileSize * 1.1547005f),
                        0, 0, drawTexture.width(), drawTexture.height());
            }
        }
    }
    
	@Override
	public boolean hasLoadedDrawingResources() {
		return hasLoadedResources;
	}

	@Override
	public void loadDrawingResources() {
        whiteTileTexture = ResourceManager.getInstance()
                .loadTexture("textures/board/white_tile.png");
        redTileTexture = ResourceManager.getInstance()
                .loadTexture("textures/board/red_tile.png");
        blueTileTexture = ResourceManager.getInstance()
                .loadTexture("textures/board/blue_tile.png");
        hasLoadedResources = true;
	}
	
	public float getTileSize() {
		return tileSize;
	}
}

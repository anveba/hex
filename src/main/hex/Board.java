package main.hex;

import java.util.Arrays;

import main.engine.ResourceManager;
import main.engine.graphics.*;

public class Board implements Drawable2D{
    private Tile[][] board;
    private boolean hasLoadedResources;
    private Texture whiteTileTexture;

    public Board(int size){
        if(size <= 0){ throw new HexException();}

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
            throw new HexException();
        }
        return board[x][y];
    }


    @Override
    public void draw(Renderer2D renderer) {
        for (int x = 0; x < getBoardSize(); x++) {
            for (int y = 0; y < getBoardSize(); y++) {
                float tileSize = 0.08f;
                float drawX = ((float)x - (float)getBoardSize() / 4.0f + 0.5f) - 0.5f * y;
                float drawY = ((float)y - (float)getBoardSize() / 2.0f + 0.5f) * 1.1547005f * 0.75f;

                drawX *= tileSize;
                drawY *= tileSize;
                
                renderer.draw(whiteTileTexture, 
                        drawX, drawY, (tileSize), (tileSize * 1.1547005f),
                        0, 0, whiteTileTexture.width(), whiteTileTexture.height());
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
        hasLoadedResources = true;
	}
}

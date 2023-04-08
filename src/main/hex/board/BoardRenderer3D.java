package main.hex.board;

import main.engine.graphics.Renderer3D;
import main.engine.graphics.model.*;
import main.engine.math.*;
import main.engine.Point2;
import main.engine.graphics.*;
import main.hex.ui.GameCustomisation;
import main.hex.Game;
import main.hex.graphics.*;

public class BoardRenderer3D {
	
	private Mesh hexMesh;
	private Material tileMaterial;
	
	private final float tileSize = 1.0f;
	private final float tileHeight = 0.5f;
	
	public BoardRenderer3D() {
		hexMesh = Meshes.buildHexTile(1.154700538f * tileSize, tileHeight);
		tileMaterial = new Material(
				0.1f, 0.1f, 0.1f,
				0.8f, 0.8f, 0.8f,
				1.2f, 1.2f, 1.2f,
				1024.0f,
				null, null
				);
	}
	
	public Vector3 tileToWorld(int i, int j, int size) {
		float x = ((float)i - (float)size / 4.0f + 0.25f) - 0.5f * j;
        float y = ((float)j - (float)size / 2.0f + 0.5f) * 1.1547005f * 0.75f;
        x *= tileSize; y *= tileSize;
        return new Vector3(x, -tileHeight / 2.0f, y);
	}
	
	public Point2 screenToTile(float screenX, float screenY, int size) {
		
		Camera cam = Game.getInstance().getCamera();
    	
		//Generate ray
    	float height = cam.getNear() * (float)Math.tan(cam.getVerticalFOV() * 0.5f) * 2.0f;
    	float width = height;
    	
    	float u = screenX * width / 2.0f;
    	float v = screenY * height / 2.0f;
    	
    	Matrix4 rotation = Matrix4.rotateYawPitchRoll(cam.getYaw(), cam.getPitch(), cam.getRoll());
		
    	Vector4 forward = Matrix4.multiply(rotation, new Vector4(0.0f, 0.0f, -1.0f, 0.0f));
    	Vector4 up = Matrix4.multiply(rotation, new Vector4(0.0f, 1.0f, 0.0f, 0.0f));
    	Vector4 right = Matrix4.multiply(rotation, new Vector4(1.0f, 0.0f, 0.0f, 0.0f));
    	
    	Vector4 rayDir = Vector4.add(Vector4.add(
    			Vector4.multiply(u, right), 
    			Vector4.multiply(v, up)),
    			Vector4.multiply(cam.getNear(), forward));
    	rayDir = Vector4.multiply(1.0f / rayDir.length(), rayDir);
    	
    	//Calculate intersection of plane at y = boardY
    	float boardY = 0.0f;
    	
    	float dist = (boardY - cam.getY()) / rayDir.y; 
    	float worldX = cam.getX() + rayDir.x * dist;
    	float worldZ = cam.getZ() + rayDir.z * dist;
    	
    	//Inverse tileToWorld
    	float tileX = worldX / tileSize;
    	float tileY = worldZ / tileSize;
    	tileY = tileY / (1.1547005f * 0.75f) - 0.5f + (float)size / 2.0f;
    	tileX = tileX + 0.5f * tileY - 0.25f + (float)size / 4.0f;
    	return new Point2((int)(tileX + 0.5f), (int)(tileY + 0.5f));
	}

	public void draw(Renderer3D renderer, Board board, GameCustomisation custom) {
		Point2 hovered = screenToTile(Game.getInstance().getControlsListener().getCursorX(),
				Game.getInstance().getControlsListener().getCursorY(), board.size());
		for (int i = 0; i < board.size(); i++) {
			for (int j = 0; j < board.size(); j++) {
				
				Tile tile = board.getTileAtPosition(i, j);
				
				Vector3 position = tileToWorld(i, j, board.size());
				Vector3 rotation = new Vector3(0.0f, 0.0f, 0.0f);
				
				Colour drawColour = Colour.White;
				Colour col = tile.getColour() == TileColour.WHITE ? Colour.White :
					(tile.getColour() == TileColour.BLUE ? Colour.Blue : Colour.Red);
				
				if (hovered.getX() == i && hovered.getY() == j)
					col = Colour.multiply(col, Colour.LightGrey);
				drawColour = Colour.multiply(drawColour, col);
				renderer.draw(hexMesh, tileMaterial, position, rotation, col);
				
			}
		}
	}
}

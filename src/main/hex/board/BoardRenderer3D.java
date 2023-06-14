package main.hex.board;

import main.engine.graphics.Renderer3D;
import main.engine.graphics.model.*;
import main.engine.math.*;

import java.awt.Color;

import main.engine.ResourceManager;
import main.engine.graphics.*;
import main.hex.Game;
import main.hex.GameCustomisation;
import main.hex.graphics.*;
import main.hex.player.PlayerSkin;
import main.hex.resources.TextureLibrary;

/**
 * Responsible for drawing the board in 3D.
 * @author Andreas - s214971
 *
 */
public class BoardRenderer3D {
	
	private Mesh hexMesh;
	private Mesh tableMesh;
	private Material tableMaterial;
	
	private final float tileSize = 1.0f;
	private final float tileHeight = 0.5f;
	private final float tableSize = 32.0f;
	private final float tableHeight = 2.0f;
	
	public BoardRenderer3D() {
		hexMesh = Meshes.buildHexTile(1.154700538f * tileSize, tileHeight);
		tableMesh = Meshes.buildParallelepipedMesh( 
				new Vector3(tableSize, 0.0f, 0.0f), 
				new Vector3(0.0f, -tableHeight, 0.0f), 
				new Vector3(0.0f, 0.0f, tableSize));
		tableMaterial = new Material(
			0.02f, 0.02f, 0.02f,
			0.9f, 0.9f, 0.9f,
			0.2f, 0.2f, 0.2f,
			256.0f, 0.02f, 0.01f,
			ResourceManager.getInstance().loadTexture("textures/oak.jpg"), null
			);
	}
	
	public Material buildTileMaterial(PlayerSkin skin, boolean isWhite) {
		float reflectance = 0.04f;
		if (!isWhite)
			reflectance = 0.04f;
		return new Material(
			0.02f, 0.02f, 0.02f,
			0.8f, 0.8f, 0.8f,
			0.6f, 0.6f, 0.6f,
			2048.0f, reflectance, 0.0f,
			skin.getTexture(), null
		);
	}
	
	public Material buildBorderMaterial() {
		return new Material(
			0.02f, 0.02f, 0.02f,
			0.8f, 0.8f, 0.8f,
			0.6f, 0.6f, 0.6f,
			2048.0f, 0.015f, 0.005f,
			TextureLibrary.WHITE_PX.getTexture(), null
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
    	
		//Generate cursor-to-world ray
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
		
		drawBorder(renderer, board, custom);
		
		drawTiles(renderer, board, custom);
		
		drawTable(renderer, board, custom);
	}
	
	private void drawBorder(Renderer3D renderer, Board board, GameCustomisation custom) {
		float borderDepth = tileHeight;
		float borderYOffset = -0.2f;
		
		float c = 0.866025404f; //cos(30)
		float drawnBoardHeight = ((board.size() - 1.0f) * 0.75f + 1.0f) / c;
		float drawnBoardWidth = board.size();
		float borderSlant = (drawnBoardWidth + 0.5f) * 0.5f;
		float offset = drawnBoardWidth * 0.25f;
		
		Mesh borderMesh =  Meshes.buildParallelepipedMesh(
				new Vector3(1.0f, 0.0f, 0.0f), 
				new Vector3(-borderSlant, 0.0f, drawnBoardHeight), 
				new Vector3(0.0f, borderDepth, 0.0f));

		renderer.draw(borderMesh, buildBorderMaterial(), 
				new Vector3(-borderSlant + offset - 0.25f * c, -borderDepth + borderYOffset, -drawnBoardHeight * 0.5f), new Vector3(), 
				custom.getPlayer1Skin().getTint());
		renderer.draw(borderMesh, buildBorderMaterial(), 
				new Vector3(borderSlant + offset - 0.5f * c, -borderDepth + borderYOffset, -drawnBoardHeight * 0.5f), new Vector3(), 
				custom.getPlayer1Skin().getTint());
		
		
		borderMesh =  Meshes.buildParallelepipedMesh(
				new Vector3(-0.5f, 0.0f, 1.0f), 
				new Vector3(-drawnBoardWidth, 0.0f, 0.0f), 
				new Vector3(0.0f, borderDepth, 0.0f));

		renderer.draw(borderMesh, buildBorderMaterial(), 
				new Vector3(0.25f * c + borderSlant + offset, -borderDepth + borderYOffset + 0.001f, -drawnBoardHeight * 0.5f - 0.5f), new Vector3(), 
				custom.getPlayer2Skin().getTint());
		renderer.draw(borderMesh, buildBorderMaterial(), 
				new Vector3(0.25f * c + offset, -borderDepth + borderYOffset + 0.001f, drawnBoardHeight * 0.5f - 0.5f), new Vector3(), 
				custom.getPlayer2Skin().getTint());
	}
	
	private void drawTiles(Renderer3D renderer, Board board, GameCustomisation custom) {
		Point2 hovered = screenToTile(Game.getInstance().getControlsListener().getCursorX(),
				Game.getInstance().getControlsListener().getCursorY(), board.size());
		for (int i = 0; i < board.size(); i++) {
			for (int j = 0; j < board.size(); j++) {
				
				Tile tile = board.getTileAtPosition(i, j);
				
				Vector3 position = tileToWorld(i, j, board.size());
				Vector3 rotation = new Vector3(0.0f, 0.0f, 0.0f);
				
				PlayerSkin skin = tile.getColour() == TileColour.WHITE ? custom.getBlankSkin() :
					(tile.getColour() == TileColour.PLAYER1 ? custom.getPlayer1Skin() : custom.getPlayer2Skin());
				
				Colour col = skin.getTint();
				if (hovered.getX() == i && hovered.getY() == j)
					col = Colour.multiply(col, Colour.LightGrey);
				if (tile.getColour() != TileColour.WHITE)
					position.y += 0.2f;
				renderer.draw(hexMesh, buildTileMaterial(skin, skin == custom.getBlankSkin()), position, rotation, col);
			}
		}
	}
	
	private void drawTable(Renderer3D renderer, Board board, GameCustomisation custom) {
		renderer.draw(tableMesh, tableMaterial, 
				new Vector3(-tableSize / 2.0f, -tileHeight, -tableSize / 2.0f), 
				new Vector3(), 
				Colour.White);
	}
}

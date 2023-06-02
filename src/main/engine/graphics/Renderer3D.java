package main.engine.graphics;

import static org.lwjgl.opengl.GL33.*;

import main.engine.*;
import main.engine.graphics.model.*;
import main.engine.math.*;
import main.hex.Game;

import main.engine.input.*;

public class Renderer3D {
	
	private MeshRenderer meshRenderer;
	private SkyboxRenderer skyboxRenderer;
	
	public Renderer3D(Camera camera, GraphicsContext context) {
        meshRenderer = new MeshRenderer(camera, context);
        skyboxRenderer = new SkyboxRenderer(camera, context);
    }
	
	public void draw(Mesh mesh, Material mat, Vector3 position, 
    		Vector3 rotation, Colour colour) {
    	meshRenderer.draw(mesh, mat, position, rotation, colour);
    }
	
	public void drawSkybox(Cubemap cubemap) {
		skyboxRenderer.draw(cubemap);
    }
}

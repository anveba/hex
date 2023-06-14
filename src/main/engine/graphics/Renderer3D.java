package main.engine.graphics;

import static org.lwjgl.opengl.GL33.*;

import main.engine.*;
import main.engine.graphics.model.*;
import main.engine.math.*;
import main.hex.Game;

import main.engine.input.*;

/**
 * A class composed of 3D renderers such as mesh and skybox renderers.
 * @author Andreas - s214971
 *
 */
public class Renderer3D {
	
	private MeshRenderer meshRenderer;
	private SkyboxRenderer skyboxRenderer;

    private Cubemap skybox;
    private float environmentStrength;
	
	public Renderer3D(Camera camera, GraphicsContext context) {
        meshRenderer = new MeshRenderer(camera, context);
        skyboxRenderer = new SkyboxRenderer(camera, context);
        this.skybox = null;
        environmentStrength = 1.0f;
    }
	
	public void draw(Mesh mesh, Material mat, Vector3 position, 
    		Vector3 rotation, Colour colour) {
		if (skybox == null)
			throw new EngineException("Cannot draw without a skybox set");
    	meshRenderer.draw(mesh, mat, position, rotation, colour, skybox, environmentStrength);
    }
	
	public void drawSkybox() {
		skyboxRenderer.draw(skybox, environmentStrength);
    }
	
	public void setSkybox(Cubemap cubemap, float strength) {
		if (strength < 0.0f)
			throw new EngineException("Strength was negative");
		this.skybox = cubemap;
		this.environmentStrength = strength;
	}
	
	public Cubemap getSkybox() {
		return skybox;
	}
}

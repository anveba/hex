package main.engine.graphics;

import static org.lwjgl.opengl.GL33.*;

import main.engine.EngineException;
import main.engine.ResourceManager;
import main.engine.math.Matrix4;

public class SkyboxRenderer {
	
    private static Shader skyboxShader = null;
    
    private static Shader getSkyboxShader() {
    	if (skyboxShader == null) {
    		skyboxShader = ResourceManager.getInstance()
    				.loadShader("shaders/renderer3D_skybox");
    	}
    	return skyboxShader;
    }
    
    private GraphicsContext context;
    private Camera camera;
    
    private int vaoHandle, vboHandle;
	
	public SkyboxRenderer(Camera camera, GraphicsContext context) {
        if (context == null)
            throw new EngineException("The context is null");
        if (camera == null)
        	throw new EngineException("Camera was null");
        this.context = context;
        this.camera = camera;
        
        float[] cubeVertices = {
	        -1.0f,  1.0f, -1.0f,
	        -1.0f, -1.0f, -1.0f,
	        1.0f, -1.0f, -1.0f,
	        1.0f, -1.0f, -1.0f,
	        1.0f,  1.0f, -1.0f,
	        -1.0f,  1.0f, -1.0f,

	        -1.0f, -1.0f,  1.0f,
	        -1.0f, -1.0f, -1.0f,
	        -1.0f,  1.0f, -1.0f,
	        -1.0f,  1.0f, -1.0f,
	        -1.0f,  1.0f,  1.0f,
	        -1.0f, -1.0f,  1.0f,

	        1.0f, -1.0f, -1.0f,
	        1.0f, -1.0f,  1.0f,
	        1.0f,  1.0f,  1.0f,
	        1.0f,  1.0f,  1.0f,
	        1.0f,  1.0f, -1.0f,
	        1.0f, -1.0f, -1.0f,

	        -1.0f, -1.0f,  1.0f,
	        -1.0f,  1.0f,  1.0f,
	        1.0f,  1.0f,  1.0f,
	        1.0f,  1.0f,  1.0f,
	        1.0f, -1.0f,  1.0f,
	        -1.0f, -1.0f,  1.0f,

	        -1.0f,  1.0f, -1.0f,
	        1.0f,  1.0f, -1.0f,
	        1.0f,  1.0f,  1.0f,
	        1.0f,  1.0f,  1.0f,
	        -1.0f,  1.0f,  1.0f,
	        -1.0f,  1.0f, -1.0f,

	        -1.0f, -1.0f, -1.0f,
	        -1.0f, -1.0f,  1.0f,
	        1.0f, -1.0f, -1.0f,
	        1.0f, -1.0f, -1.0f,
	        -1.0f, -1.0f,  1.0f,
	        1.0f, -1.0f,  1.0f
	    };
        
        vaoHandle = glGenVertexArrays();
        glBindVertexArray(vaoHandle);
        
        final int floatSize = 4;

        vboHandle = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboHandle);
        glBufferData(GL_ARRAY_BUFFER, cubeVertices, GL_STATIC_DRAW);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * floatSize, 0);
        glEnableVertexAttribArray(0);
    }
	
	@Override
	public void finalize() {
		if (vaoHandle != 0)
			glDeleteVertexArrays(vaoHandle);
		if (vboHandle != 0)
			glDeleteBuffers(vboHandle);
	}
	
	public void draw(Cubemap cubemap) {
		if (cubemap == null)
			throw new EngineException("Null argument given");
		setupPreDraw();
		setUniforms(cubemap);
		drawCube();
	}

	private void setupPreDraw() {
    	glEnable(GL_DEPTH_TEST);
    	glDepthFunc(GL_LEQUAL);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_MULTISAMPLE);
    }
	
	private void setUniforms(Cubemap cubemap) {
		getSkyboxShader().use();
		
		Matrix4 view;
		{
			
			Matrix4 rotMat = Matrix4.rotateYawPitchRoll(
					camera.getYaw(), camera.getPitch(), camera.getRoll()).transpose();
			Matrix4 transMat = Matrix4.translate(
					-camera.getX(), -camera.getY(), -camera.getZ());
			view = Matrix4.multiply(rotMat, transMat);
		}
		
		Matrix4 proj = Matrix4.perspective(camera.getVerticalFOV(), 
				(float)context.getViewportWidth() / context.getViewportHeight(),
				camera.getNear(), camera.getFar());
		
		getSkyboxShader().setMat4("u_view", view);
		getSkyboxShader().setMat4("u_proj", proj);
		cubemap.use(0);
        getSkyboxShader().setInt("u_cubemap", 0);
	}
	
	private void drawCube() {
		glBindVertexArray(vaoHandle);
		glDrawArrays(GL_TRIANGLES, 0, 36);
	}
}

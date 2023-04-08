package main.engine.graphics;

import static org.lwjgl.opengl.GL33.*;

import main.engine.*;
import main.engine.graphics.model.*;
import main.engine.math.*;
import main.hex.Game;

import main.engine.input.*;

public class Renderer3D {
    private static Shader shader = null;
    
    private static Shader getShader() {
    	if (shader == null) {
    		shader = ResourceManager.getInstance()
    				.loadShader("shaders/renderer3D_mesh");
    	}
    	return shader;
    }

    private GraphicsContext context;
    private Camera camera;
	
	public Renderer3D(Camera camera, GraphicsContext context) {
        if (context == null)
            throw new EngineException("The context is null");
        if (camera == null)
        	throw new EngineException("Camera was null");
        this.context = context;
        this.camera = camera;
    }
	
    public void draw(Mesh mesh, Material mat, Vector3 position, 
    		Vector3 rotation, Colour colour) {
    	if (mesh == null || mat == null || position == null 
    			|| rotation == null || colour == null)
    		throw new EngineException("Null argument given");
    	
        setupPreDraw();

        setUniforms(mat, position, rotation, colour);
        
        float[] vertices = mesh.getVertexBuffer();
        int[] indices = mesh.getIndexBuffer();

        drawUsingBuffers(vertices, indices);
    }

	private void setUniforms(Material mat, Vector3 position, Vector3 rotation, Colour colour) {
		getShader().use();

		Matrix4 model;
		{
			Matrix4 rotMat = Matrix4.rotateYawPitchRoll(rotation.x, rotation.y, rotation.z);
			Matrix4 transMat = Matrix4.translate(position.x, position.y, position.z);
			model = Matrix4.multiply(transMat, rotMat);
		}
		
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
		
        getShader().setMat4("u_model", model);
        getShader().setMat4("u_view", view);
        getShader().setMat4("u_proj", proj);
        
        Vector4 viewSpaceLightDir = Matrix4.multiply(view, new Vector4(0.2f, -1.0f, 0.2f, 0.0f));
        getShader().setVec3("u_light.direction", viewSpaceLightDir.x, viewSpaceLightDir.y, viewSpaceLightDir.z);
        getShader().setVec3("u_light.ambient", 0.8f, 0.8f, 0.8f);
        getShader().setVec3("u_light.diffuse", 0.8f, 0.8f, 0.8f);
        getShader().setVec3("u_light.specular", 0.8f, 0.8f, 0.8f);
        
        getShader().setVec3("u_material.ambient", mat.aR, mat.aG, mat.aB);
        getShader().setVec3("u_material.diffuse", mat.dR, mat.dG, mat.dB);
        getShader().setVec3("u_material.specular", mat.sR, mat.sG, mat.sB);
        getShader().setFloat("u_material.shininess", mat.shininess);
        
        getShader().setVec4("u_col", colour.r(), colour.g(), colour.b(), colour.a());
	}
	
    private void setupPreDraw() {
    	glEnable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

	private void drawUsingBuffers(float[] vertices, int[] indices) {
		int vao = glGenVertexArrays();
        glBindVertexArray(vao);

        int floatSize = 4;
        
        int vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 8 * floatSize, 0 * floatSize);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 8 * floatSize, 3 * floatSize);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(2, 3, GL_FLOAT, false, 8 * floatSize, 5 * floatSize);
        glEnableVertexAttribArray(2);

        int ebo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

        glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);

        glDeleteBuffers(vbo);
        glDeleteBuffers(ebo);
        glDeleteVertexArrays(vao);
	}
}

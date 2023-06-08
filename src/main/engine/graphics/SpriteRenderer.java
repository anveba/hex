package main.engine.graphics;

import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL33.*;

import main.engine.EngineException;
import main.engine.ResourceManager;
import main.engine.math.Matrix4;

public class SpriteRenderer {
	
    public static final int[] quadIndices = { 0, 1, 2, 2, 3, 0 };
	
    private static Shader shader = null;
    
    private static Shader getShader() {
    	if (shader == null) {
    		shader = ResourceManager.getInstance()
    				.loadShader("shaders/renderer2D_image");
    	}
    	return shader;
    }

    private GraphicsContext context;
	
	public SpriteRenderer(GraphicsContext context) {
        if (context == null)
            throw new EngineException("The context is null");
        this.context = context;
    }
	
	public void draw(Texture tex, float x, float y, 
    		float width, float height, int sourceX, int sourceY,
            int sourceWidth, int sourceHeight, Colour colour) {
		draw(tex, x, y, width, height, sourceX, sourceY,
				sourceWidth, sourceHeight, colour, 0.0f);
	}
	
    public void draw(Texture tex, float x, float y, 
    		float width, float height, int sourceX, int sourceY,
            int sourceWidth, int sourceHeight, Colour colour, float rotation) {
    	
        setupPreDraw();

        float[] vertices = generateVertexBufferForSpriteRendering(tex, width, height, sourceX, sourceY, sourceWidth, sourceHeight);

        setImageUniforms(tex, colour, x, y, rotation);

        drawUsingBuffers(vertices, quadIndices);
    }

	private void setImageUniforms(Texture tex, Colour colour, float x, float y, float rotation) {
		getShader().use();
		
		Matrix4 model;
		{
			Matrix4 rotMat = Matrix4.rotateYawPitchRoll(0.0f, 0.0f, rotation);
			Matrix4 transMat = Matrix4.translate(x, y, 0.0f);
			model = Matrix4.multiply(transMat, rotMat);
		}

		getShader().setMat4("u_model", model);
		
        tex.use(0);
        getShader().setInt("u_tex", 0);
        getShader().setVec4("u_col", colour.r(), colour.g(), colour.b(), colour.a());
	}

	private float[] generateVertexBufferForSpriteRendering(Texture tex, float width, float height, int sourceX,
			int sourceY, int sourceWidth, int sourceHeight) {
		float aspectRatio = (float) context.getFramebufferWidth() / context.getFramebufferHeight();

        float iw = (float) 1.0f / tex.width();
        float ih = (float) 1.0f / tex.height();
        float x = -width / 2.0f;
        float y = -height / 2.0f;
        x /= aspectRatio;
        width /= aspectRatio;

        float[] vertices = { x, y, sourceX * iw, sourceY * ih, x + width, y, (sourceX + sourceWidth) * iw, sourceY * ih,
                x + width, y + height, (sourceX + sourceWidth) * iw, (sourceY + sourceHeight) * ih, x, y + height,
                sourceX * iw, (sourceY + sourceHeight) * ih };
		return vertices;
	}
	
    private void setupPreDraw() {
    	glDisable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

	private void drawUsingBuffers(float[] vertices, int[] indices) {
		int vao = glGenVertexArrays();
        glBindVertexArray(vao);

        int vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 4 * 4, 0L);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 4 * 4, 2 * 4);
        glEnableVertexAttribArray(1);

        int ebo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

        glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);

        glDeleteBuffers(vbo);
        glDeleteBuffers(ebo);
        glDeleteVertexArrays(vao);
	}
}
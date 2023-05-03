package main.engine.graphics;

import static org.lwjgl.opengl.GL33.*;

import main.engine.*;
import main.engine.font.BitmapFont;
import main.engine.font.FontCharacterData;
import main.engine.math.Vector2;

public class StringRenderer {

    private static Shader textRendererShader = null;

    private static Shader getShader() {
        if (textRendererShader == null) {
            textRendererShader = ResourceManager.getInstance()
                    .loadShader("shaders/renderer2D_text");
        }
        return textRendererShader;
    }

    private GraphicsContext context;
    
	public StringRenderer(GraphicsContext context) {
        if (context == null)
            throw new EngineException("The context is null");
        this.context = context;
    }

    public void draw(BitmapFont font, String text, 
    		float x, float y, float height, Colour colour) {

    	setupPreDraw();
    	
        setUniforms(font, colour);

        float[] vertices = generateVertexBuffer(font, text, x, y, height);

        int[] indices = generateIndexBufferForXQuads(text.length());

        drawUsingBuffers(vertices, indices);
    }

	private void setUniforms(BitmapFont font, Colour colour) {
		getShader().use();
        getShader().setInt("u_tex", 0);
        getShader().setVec4("u_col", colour.r(), colour.g(), colour.b(), colour.a());
        font.useTexture(0);
	}

	private int[] generateIndexBufferForXQuads(int quadCount) {
		int[] indices = new int[quadCount * 6];
        for (int i = 0; i < quadCount; i++) {
            indices[i * 6 + 0] = 0 + i * 4;
            indices[i * 6 + 1] = 1 + i * 4;
            indices[i * 6 + 2] = 2 + i * 4;
            indices[i * 6 + 3] = 2 + i * 4;
            indices[i * 6 + 4] = 3 + i * 4;
            indices[i * 6 + 5] = 0 + i * 4;
        }
		return indices;
	}

	private float[] generateVertexBuffer(BitmapFont font, String text, 
			float x, float y, float height) {
		float aspectRatio = (float) context.getFramebufferWidth() / context.getFramebufferHeight();

        float ibw = 1.0f / font.bitmapWidth();
        float ibh = 1.0f / font.bitmapHeight();

        Vector2 measurements = font.measureString(text);

        float sizeFactor = height / font.charHeight();

        float scaleX = sizeFactor;
        float scaleY = sizeFactor;

        x -= measurements.getX() * scaleX / 2.0f;
        y -= font.charHeight() * scaleY / 2.0f;
        x /= aspectRatio;

        float[] vertices = new float[text.length() * 4 * 4];

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            FontCharacterData d = font.getCharacterData(c);
            float cw = (d.x1() - d.x0()) * scaleX / aspectRatio;
            float ch = (d.y1() - d.y0()) * scaleY;
            float xo = d.xOffset() * scaleX;
            float yo = d.yOffset() * scaleY;
            vertices[i * 4 * 4 + 0] = x + xo;
            vertices[i * 4 * 4 + 1] = y + yo;
            vertices[i * 4 * 4 + 2] = d.x0() * ibw;
            vertices[i * 4 * 4 + 3] = d.y0() * ibh;
            vertices[i * 4 * 4 + 4] = x + xo + cw;
            vertices[i * 4 * 4 + 5] = y + yo;
            vertices[i * 4 * 4 + 6] = d.x1() * ibw;
            vertices[i * 4 * 4 + 7] = d.y0() * ibh;
            vertices[i * 4 * 4 + 8] = x + xo + cw;
            vertices[i * 4 * 4 + 9] = y + yo + ch;
            vertices[i * 4 * 4 + 10] = d.x1() * ibw;
            vertices[i * 4 * 4 + 11] = d.y1() * ibh;
            vertices[i * 4 * 4 + 12] = x + xo;
            vertices[i * 4 * 4 + 13] = y + yo + ch;
            vertices[i * 4 * 4 + 14] = d.x0() * ibw;
            vertices[i * 4 * 4 + 15] = d.y1() * ibh;

            x += d.advance() * scaleX / aspectRatio;
        }
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
	
	public float getRenderedStringWidth(BitmapFont font, String str, float height) {
		
		Vector2 measurements = font.measureString(str);
        float sizeFactor = height / font.charHeight();
        
        return measurements.getX() * sizeFactor;
	}
}

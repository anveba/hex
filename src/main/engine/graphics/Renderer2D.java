package main.engine.graphics;

import static org.lwjgl.opengl.GL33.*;

import java.util.ArrayList;
import java.util.List;

import main.engine.*;
import main.engine.font.*;

public class Renderer2D {
    
    //TODO clean up

    public static int[] quadIndices = { 0, 1, 2, 2, 3, 0 };

    private static Shader imageRendererShader = null;
    private static Shader textRendererShader = null;

    private static Shader getImageRendererShader() {
        if (imageRendererShader == null) {
            imageRendererShader = ResourceManager.getInstance()
                    .loadShader("shaders/renderer2D_image");
        }
        return imageRendererShader;
    }

    private static Shader getTextRendererShader() {
        if (textRendererShader == null) {
            textRendererShader = ResourceManager.getInstance()
                    .loadShader("shaders/renderer2D_text");
        }
        return textRendererShader;
    }

    private GraphicsContext context = null;

    public Renderer2D(GraphicsContext context) {
        if (context == null)
            throw new EngineException("the context is null");
        this.context = context;
    }

    public void draw(Texture tex, float x, float y, float width, float height, int sourceX, int sourceY,
            int sourceWidth, int sourceHeight) {
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        float aspectRatio = (float) context.getViewportWidth() / context.getViewportHeight();

        float iw = (float) 1.0f / tex.width();
        float ih = (float) 1.0f / tex.height();
        x -= width / 2.0f;
        y -= height / 2.0f;
        x /= aspectRatio;
        width /= aspectRatio;

        float[] vertices = { x, y, sourceX * iw, sourceY * ih, x + width, y, (sourceX + sourceWidth) * iw, sourceY * ih,
                x + width, y + height, (sourceX + sourceWidth) * iw, (sourceY + sourceHeight) * ih, x, y + height,
                sourceX * iw, (sourceY + sourceHeight) * ih };

        getImageRendererShader().use();

        tex.use(0);
        getImageRendererShader().setInt("u_tex", 0);

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
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, quadIndices, GL_STATIC_DRAW);

        glDrawElements(GL_TRIANGLES, quadIndices.length, GL_UNSIGNED_INT, 0);

        glDeleteBuffers(vbo);
        glDeleteBuffers(ebo);
        glDeleteVertexArrays(vao);
    }

    public void drawString(BitmapFont font, String text, float x, float y, float height) {
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        getTextRendererShader().use();
        getTextRendererShader().setInt("u_tex", 0);
        font.useTexture(0);

        float aspectRatio = (float) context.getViewportWidth() / context.getViewportHeight();

        float ibw = 1.0f / font.bitmapWidth();
        float ibh = 1.0f / font.bitmapHeight();

        Vector2 measurements = font.measureString(text);

        float sizeFactor = height / measurements.getY();

        float scaleX = sizeFactor;
        float scaleY = sizeFactor;

        x -= measurements.getX() * scaleX / 2.0f;
        y -= measurements.getY() * scaleY / 2.0f;
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

        int[] indices = new int[text.length() * 6];
        for (int i = 0; i < text.length(); i++) {
            indices[i * 6 + 0] = 0 + i * 4;
            indices[i * 6 + 1] = 1 + i * 4;
            indices[i * 6 + 2] = 2 + i * 4;
            indices[i * 6 + 3] = 2 + i * 4;
            indices[i * 6 + 4] = 3 + i * 4;
            indices[i * 6 + 5] = 0 + i * 4;
        }

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

package main.engine.graphics;

import static org.lwjgl.opengl.GL33.*;

import java.util.ArrayList;
import java.util.List;

import main.engine.*;
import main.engine.font.*;
import main.hex.player.PlayerSkin;

/**
 * A class composed of 2D renderers such as a text and sprite renderer.
 * @author Andreas - s214971
 *
 */
public class Renderer2D {

	private SpriteRenderer spriteRenderer;
	private StringRenderer stringRenderer;
	
	public Renderer2D(GraphicsContext context) {
        if (context == null)
            throw new EngineException("The context is null");
        spriteRenderer = new SpriteRenderer(context);
        stringRenderer = new StringRenderer(context);
    }

    // Without rotation
    public void drawSprite(Texture tex, float x, float y, 
    		float width, float height, int sourceX, int sourceY,
            int sourceWidth, int sourceHeight, Colour colour) {
    	spriteRenderer.draw(tex, x, y, width, height, 
    			sourceX, sourceY, sourceWidth, sourceHeight, 
    			colour);
    }

    // With rotation
    public void drawSprite(Texture tex, float x, float y,
                           float width, float height, int sourceX, int sourceY,
                           int sourceWidth, int sourceHeight, Colour colour, float rotation) {
        spriteRenderer.draw(tex, x, y, width, height,
                sourceX, sourceY, sourceWidth, sourceHeight,
                colour, rotation);
    }

    // Simple with rotation
    public void drawSprite(Texture tex, Colour col, float rotation, float x, float y, float width, float height) {
        spriteRenderer.draw(tex, x, y, width, height, 0, 0, tex.width(), tex.height(), col, rotation);
    }
    
    public void drawString(BitmapFont font, String text, 
    		float x, float y, float height, Colour colour) {
    	stringRenderer.draw(font, text, x, y, height, colour);
    }
    
    public float getRenderedStringWidth(BitmapFont font, String str, float height) {
    	return stringRenderer.getRenderedStringWidth(font, str, height);
    }
}

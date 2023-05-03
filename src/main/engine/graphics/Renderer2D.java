package main.engine.graphics;

import static org.lwjgl.opengl.GL33.*;

import java.util.ArrayList;
import java.util.List;

import main.engine.*;
import main.engine.font.*;
import main.hex.player.PlayerSkin;

public class Renderer2D {

	private SpriteRenderer spriteRenderer;
	private StringRenderer stringRenderer;
	
	public Renderer2D(GraphicsContext context) {
        if (context == null)
            throw new EngineException("The context is null");
        spriteRenderer = new SpriteRenderer(context);
        stringRenderer = new StringRenderer(context);
    }
	
    public void drawSprite(Texture tex, float x, float y, 
    		float width, float height, int sourceX, int sourceY,
            int sourceWidth, int sourceHeight, Colour colour) {
    	spriteRenderer.draw(tex, x, y, width, height, 
    			sourceX, sourceY, sourceWidth, sourceHeight, 
    			colour);
    }

    public void drawSprite(Texture tex, Colour col, float x, float y, float width, float height) {
        spriteRenderer.draw(tex, x, y, width, height, 0, 0, tex.width(), tex.height(), col);
    }
    
    public void drawString(BitmapFont font, String text, 
    		float x, float y, float height, Colour colour) {
    	stringRenderer.draw(font, text, x, y, height, colour);
    }
    
    public float getRenderedStringWidth(BitmapFont font, String str, float height) {
    	return stringRenderer.getRenderedStringWidth(font, str, height);
    }
}

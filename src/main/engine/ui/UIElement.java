package main.engine.ui;

import main.engine.EngineException;
import main.engine.TimeRecord;
import main.engine.graphics.Colour;
import main.engine.graphics.Renderer2D;

/**
 * The base class for all UI elements. 
 * @author Andreas - s214971
 *
 */
public abstract class UIElement {
	
	private UIElement parent;
	private boolean isHidden;
	
	protected UIElement() {
		parent = null;
		isHidden = false;
	}
	
	public abstract float getX();
	public abstract float getY();
	public abstract void setPosition(float x, float y);

	public final void draw(Renderer2D renderer, float offsetX, float offsetY, Colour colour) {
		if(isHidden()) return;

		drawElement(renderer, offsetX, offsetY, colour);
	}

	protected abstract void drawElement(Renderer2D renderer, float offsetX, float offsetY, Colour colour);
    
    void setParent(UIElement parent) {
    	if (parent == null)
    		throw new EngineException("Tried to set parent to null");
    	this.parent = parent;
    }
    
    void removeParent() {
    	parent = null;
    }
    
    /**
     * @return Returns null if no element has no parent.
     */
    UIElement getParent() {
    	return parent;
    }

	public final void update(TimeRecord elapsed) {
		if(isHidden()) return;

		updateElement(elapsed);
	}
    protected abstract void updateElement(TimeRecord elapsed);

	public void hide() {
		isHidden = true;
	}
	
	public void show() {
		isHidden = false;
	}
	
	public boolean isHidden() {
		return isHidden;
	}
}

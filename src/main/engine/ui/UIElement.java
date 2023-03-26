package main.engine.ui;

import main.engine.EngineException;
import main.engine.graphics.Colour;
import main.engine.graphics.Renderer2D;

public abstract class UIElement {
	
	private UIElement parent;
	
	protected UIElement() {
		parent = null;
	}
	
	public abstract float getX();
	public abstract float getY();
	public abstract void setPosition(float x, float y);
    void draw(Renderer2D renderer, float offsetX, float offsetY, Colour colour) { }
    
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
}

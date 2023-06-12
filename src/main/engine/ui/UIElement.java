package main.engine.ui;

import main.engine.EngineException;
import main.engine.TimeRecord;
import main.engine.graphics.Colour;
import main.engine.graphics.Renderer2D;

/**
 * 
 * @author andreas
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

	protected void draw(Renderer2D renderer, float offsetX, float offsetY, Colour colour) {};
    
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
    
    public abstract void update(TimeRecord elapsed);

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

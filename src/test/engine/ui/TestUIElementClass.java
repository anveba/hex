package test.engine.ui;

import main.engine.TimeRecord;
import main.engine.graphics.Colour;
import main.engine.graphics.Renderer2D;
import main.engine.ui.UIElement;

class TestUIElementClass extends UIElement {

	@Override
	public float getX() {
		return Float.NaN;
	}

	@Override
	public float getY() {
		return Float.NaN;
	}

	@Override
	public void setPosition(float x, float y) {
	}

	@Override
	protected void drawElement(Renderer2D renderer, float offsetX, float offsetY, Colour colour) {

	}

	@Override
	protected void updateElement(TimeRecord elapsed) {
		
	}
}

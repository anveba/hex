package test.engine.ui;

import main.engine.TimeRecord;
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
	public void update(TimeRecord elapsed) {
		
	}
}

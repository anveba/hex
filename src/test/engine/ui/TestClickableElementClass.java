package test.engine.ui;

import main.engine.ui.ClickArgs;
import main.engine.ui.Clickable;
import main.engine.ui.HoverArgs;

class TestClickableElementClass extends TestUIElementClass implements Clickable {

	@Override
	public void onClick(ClickArgs args) {
	}

	@Override
	public boolean containsPosition(float x, float y) {
		return false;
	}

	@Override
	public void updateCursorPosition(HoverArgs args) {
	}
}

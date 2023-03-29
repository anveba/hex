package test.engine.ui;

import main.engine.TimeRecord;
import main.engine.input.ControlsArgs;
import main.engine.ui.ClickArgs;
import main.engine.ui.Clickable;
import main.engine.ui.HoverArgs;
import main.engine.ui.TextInputArgs;

class TestClickableElementClass extends TestUIElementClass implements Clickable {

	@Override
	public void processClick(ClickArgs args) {
	}

	@Override
	public boolean containsPosition(float x, float y) {
		return false;
	}

	@Override
	public void updateCursorPosition(HoverArgs args) {
	}

	@Override
	public void processTextInput(TextInputArgs args) {

	}

	@Override
	public void processControlsInput(ControlsArgs args) {

	}
	
	@Override
	public void update(TimeRecord elapsed) {
		
	}
}

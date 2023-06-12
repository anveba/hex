package test.engine.ui;

import main.engine.TimeRecord;
import main.engine.input.ControlsArgs;
import main.engine.ui.Clickable;
import main.engine.ui.callback.ClickArgs;
import main.engine.ui.callback.HoverArgs;
import main.engine.ui.callback.TextInputArgs;

class TestClickableElementClass extends TestUIElementClass implements Clickable {

	@Override
	public void processClickRelease(ClickArgs args) {
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

	@Override
	public void processClickDown(ClickArgs args) {
		
	}
}

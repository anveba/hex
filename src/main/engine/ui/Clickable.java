package main.engine.ui;

import main.engine.input.ControlsArgs;
import main.engine.ui.callback.ClickArgs;
import main.engine.ui.callback.TextInputArgs;

public interface Clickable extends Hoverable {
    void processClickRelease(ClickArgs args);
    
	boolean containsPosition(float x, float y);
    
    void processTextInput(TextInputArgs args);
    
    void processControlsInput(ControlsArgs args);

	void processClickDown(ClickArgs args);
}

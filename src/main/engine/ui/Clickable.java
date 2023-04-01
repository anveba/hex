package main.engine.ui;

import main.engine.input.ControlsArgs;

public interface Clickable {
    void processClick(ClickArgs args);
    
	boolean containsPosition(float x, float y);

    void updateCursorPosition(HoverArgs args);
    
    void processTextInput(TextInputArgs args);
    
    void processControlsInput(ControlsArgs args);
}

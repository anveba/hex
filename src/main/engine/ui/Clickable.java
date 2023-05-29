package main.engine.ui;

import main.engine.input.ControlsArgs;

public interface Clickable extends Hoverable {
    void processClick(ClickArgs args);
    
	boolean containsPosition(float x, float y);
    
    void processTextInput(TextInputArgs args);
    
    void processControlsInput(ControlsArgs args);
}

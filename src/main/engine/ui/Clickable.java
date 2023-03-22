package main.engine.ui;

public interface Clickable {
    void onClick(ClickArgs args);
    
	boolean containsPosition(float x, float y);

    void updateCursorPosition(HoverArgs args);
}

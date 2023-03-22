package main.engine.ui;

public interface Clickable {
    void onClick(ClickArgs args);

    void updateCursorPosition(HoverArgs args);
}

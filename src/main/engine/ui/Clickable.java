package main.engine.ui;

interface Clickable {
    void onClick(ClickArgs args);

    void updateCursorPosition(HoverArgs args);
}

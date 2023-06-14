package main.engine.graphics;

/**
 * Represents the context in which rendering happens.
 * @author Andreas - s214971
 *
 */
public interface GraphicsContext {
    int getViewportWidth();
    int getViewportHeight();
    int getFramebufferWidth();
    int getFramebufferHeight();
}

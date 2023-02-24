package engine.input;

import java.util.*;
import java.util.function.*;

import static org.lwjgl.glfw.GLFW.*;
import engine.*;
import engine.graphics.GraphicsContext;

public class ControlsListener {
    
    private Map<Controls, List<ControlsCallback>> onPressCallbacks, onReleaseCallbacks;
    private List<CursorMoveCallback> onCursorMoveCallbacks;
    
    private float cursorX, cursorY;
    
    public ControlsListener(
            Consumer<BiConsumer<Controls, InputType>> inputProcessorSetter,
            Consumer<BiConsumer<Float, Float>> mouseProcessorSetter) {
        
        if (inputProcessorSetter == null | mouseProcessorSetter == null)
            throw new IllegalArgumentException("No callbacks given (null passed)");
            
        onPressCallbacks = new HashMap<>();
        onReleaseCallbacks = new HashMap<>();
        onCursorMoveCallbacks = new ArrayList<>();
        
        inputProcessorSetter.accept(this::processInput);
        mouseProcessorSetter.accept(this::processCursorPosition);
    }
    
    private void processInput(Controls c, InputType t) {
        if (t == InputType.PRESSED) {
            if (onPressCallbacks.containsKey(c)) {
                for (var cb : onPressCallbacks.get(c)) {
                    cb.onControlsInput(new ControlsArgs());
                }
            }
        } else if (t == InputType.RELEASED) {
            if (onReleaseCallbacks.containsKey(c)) {
                for (var cb : onReleaseCallbacks.get(c)) {
                    cb.onControlsInput(new ControlsArgs());
                }
            }
        }
        else {
            throw new EngineException("Unknown input type: " + t);
        }
    }
    
    private void processCursorPosition(float x, float y) {
        cursorX = x;
        cursorY = y;
        for (var c : onCursorMoveCallbacks) {
            c.onCursorMove(x, y);
        }
    }
    
    public void addOnPressCallback(Controls c, ControlsCallback callback) {
        List<ControlsCallback> callbacks;
        if (!onPressCallbacks.containsKey(c)) {
            callbacks = new ArrayList<>();
            onPressCallbacks.put(c, callbacks);
        } else {
            callbacks = onPressCallbacks.get(c);
        }
        assert callbacks != null;
        callbacks.add(callback);
    }
    
    public void addOnReleaseCallback(Controls c, ControlsCallback callback) {
        List<ControlsCallback> callbacks;
        if (!onReleaseCallbacks.containsKey(c)) {
            callbacks = new ArrayList<>();
            onReleaseCallbacks.put(c, callbacks);
        } else {
            callbacks = onReleaseCallbacks.get(c);
        }
        assert callbacks != null;
        callbacks.add(callback);
    }
    
    public boolean removeOnPressCallback(Controls c, ControlsCallback callback) {
        List<ControlsCallback> callbacks;
        if (!onPressCallbacks.containsKey(c))
            return false;
        callbacks = onPressCallbacks.get(c);
        assert callbacks != null;
        return callbacks.remove(callback);
    }
    
    public boolean removeOnReleaseCallback(Controls c, ControlsCallback callback) {
        List<ControlsCallback> callbacks;
        if (!onReleaseCallbacks.containsKey(c))
            return false;
        callbacks = onReleaseCallbacks.get(c);
        assert callbacks != null;
        return callbacks.remove(callback);
    }
    
    public void addOnCursorMoveCallback(CursorMoveCallback callback) {
        onCursorMoveCallbacks.add(callback);
    }
    
    public boolean removeOnCursorMoveCallback(CursorMoveCallback callback) {
        return onCursorMoveCallbacks.remove(callback);
    }
    
    public float getCursorX() { return cursorX; }
    public float getCursorY() { return cursorY; }
}

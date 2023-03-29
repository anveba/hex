package main.engine.input;

import java.util.*;
import java.util.function.*;

import main.engine.*;
import main.engine.graphics.*;

import static org.lwjgl.glfw.GLFW.*;

public class ControlsListener {
    
    private Map<Controls, List<ControlsCallback>> onButtonPressCallbacks, onButtonReleaseCallbacks;
    private List<CursorMoveCallback> onCursorMoveCallbacks;
    private List<TextInputCallback> onTextInputCallbacks;
    private List<ControlsCallback> onAnyPressCallbacks, onAnyReleaseCallbacks;
    
    private float cursorX, cursorY;
    
    public ControlsListener(
            Consumer<BiConsumer<Controls, InputType>> inputProcessorSetter,
            Consumer<BiConsumer<Float, Float>> mouseProcessorSetter,
            Consumer<Consumer<Character>> textInputProcessorSetter) {
        
        if (inputProcessorSetter == null 
        		|| mouseProcessorSetter == null 
        		|| textInputProcessorSetter == null)
            throw new IllegalArgumentException("No callbacks given (null passed)");
            
        onButtonPressCallbacks = new HashMap<>();
        onButtonReleaseCallbacks = new HashMap<>();
        onCursorMoveCallbacks = new ArrayList<>();
        onTextInputCallbacks = new ArrayList<>();
        onAnyPressCallbacks = new ArrayList<>();
        onAnyReleaseCallbacks = new ArrayList<>();
        
        inputProcessorSetter.accept(this::processKeyInput);
        mouseProcessorSetter.accept(this::processCursorPosition);
        textInputProcessorSetter.accept(this::processTextInput);
    }
    
    private void processKeyInput(Controls c, InputType t) {
        if (t == InputType.PRESSED) {
        	var args = new ControlsArgs(c);
            if (onButtonPressCallbacks.containsKey(c)) {
                for (var cb : onButtonPressCallbacks.get(c)) 
                    cb.onControlsInput(args);
            }
            for (var cb : onAnyPressCallbacks)
        		cb.onControlsInput(args);
        } else if (t == InputType.RELEASED) {
        	var args = new ControlsArgs(c);
            if (onButtonReleaseCallbacks.containsKey(c)) {
                for (var cb : onButtonReleaseCallbacks.get(c)) 
                    cb.onControlsInput(args);
            }
            for (var cb : onAnyReleaseCallbacks)
        		cb.onControlsInput(args);
        }
        
        if (c == Controls.BACKSPACE && (t == InputType.PRESSED || t == InputType.REPEAT))
        	for (var cb : onTextInputCallbacks)
        		cb.onTextInput('\b');
    }
    
    private void processCursorPosition(float x, float y) {
        cursorX = x;
        cursorY = y;
        for (var c : onCursorMoveCallbacks) {
            c.onCursorMove(x, y);
        }
    }
    
    private void processTextInput(char ch) 
    {
    	for (var c : onTextInputCallbacks)
    		c.onTextInput(ch);
    }
    
    public void addOnButtonPressCallback(Controls c, ControlsCallback callback) {
        List<ControlsCallback> callbacks;
        if (!onButtonPressCallbacks.containsKey(c)) {
            callbacks = new ArrayList<>();
            onButtonPressCallbacks.put(c, callbacks);
        } else {
            callbacks = onButtonPressCallbacks.get(c);
        }
        assert callbacks != null;
        callbacks.add(callback);
    }
    
    public void addOnButtonReleaseCallback(Controls c, ControlsCallback callback) {
        List<ControlsCallback> callbacks;
        if (!onButtonReleaseCallbacks.containsKey(c)) {
            callbacks = new ArrayList<>();
            onButtonReleaseCallbacks.put(c, callbacks);
        } else {
            callbacks = onButtonReleaseCallbacks.get(c);
        }
        assert callbacks != null;
        callbacks.add(callback);
    }
    
    public boolean removeOnButtonPressCallback(Controls c, ControlsCallback callback) {
        List<ControlsCallback> callbacks;
        if (!onButtonPressCallbacks.containsKey(c))
            return false;
        callbacks = onButtonPressCallbacks.get(c);
        assert callbacks != null;
        return callbacks.remove(callback);
    }
    
    public boolean removeOnButtonReleaseCallback(Controls c, ControlsCallback callback) {
        List<ControlsCallback> callbacks;
        if (!onButtonReleaseCallbacks.containsKey(c))
            return false;
        callbacks = onButtonReleaseCallbacks.get(c);
        assert callbacks != null;
        return callbacks.remove(callback);
    }
    
    public void addOnAnyPressCallback(ControlsCallback callback) {
    	if (callback == null)
    		throw new EngineException("Callback was null");
    	onAnyPressCallbacks.add(callback);
    }
    
    public boolean removeOnAnyPressCallback(ControlsCallback callback) {
    	return onAnyPressCallbacks.remove(callback);
    }
    
    public void addOnAnyReleaseCallback(ControlsCallback callback) {
    	if (callback == null)
    		throw new EngineException("Callback was null");
    	onAnyReleaseCallbacks.add(callback);
    }
    
    public boolean removeOnAnyReleaseCallback(ControlsCallback callback) {
    	return onAnyReleaseCallbacks.add(callback);
    }
    
    public void addOnCursorMoveCallback(CursorMoveCallback callback) {
    	if (callback == null)
    		throw new EngineException("Callback was null");
        onCursorMoveCallbacks.add(callback);
    }
    
    public boolean removeOnCursorMoveCallback(CursorMoveCallback callback) {
        return onCursorMoveCallbacks.remove(callback);
    }
    
    public void addTextInputCallback(TextInputCallback callback) {
    	if (callback == null)
    		throw new EngineException("Callback was null");
    	onTextInputCallbacks.add(callback);
    }
    
    public boolean removeTextInputCallback(TextInputCallback callback) {
        return onTextInputCallbacks.remove(callback);
    }
    
    public float getCursorX() { return cursorX; }
    public float getCursorY() { return cursorY; }
}

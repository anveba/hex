package main.engine.input;

import java.util.*;
import java.util.function.*;

import main.engine.*;
import main.engine.graphics.*;

import static org.lwjgl.glfw.GLFW.*;

public class ControlsListener {
    
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
        
        onCursorMoveCallbacks = new ArrayList<>();
        onTextInputCallbacks = new ArrayList<>();
        onAnyPressCallbacks = new ArrayList<>();
        onAnyReleaseCallbacks = new ArrayList<>();
        
        inputProcessorSetter.accept(this::processKeyInput);
        mouseProcessorSetter.accept(this::processCursorPosition);
        textInputProcessorSetter.accept(this::processTextInput);
    }
    
    private void processKeyInput(Controls c, InputType t) {
        callRelevantCallbacks(c, t);
    }
    
    private void callRelevantCallbacks(Controls c, InputType t) {
    	if (t == InputType.PRESSED) {
        	var args = new ControlsArgs(c);
            for (var cb : onAnyPressCallbacks)
        		cb.onControlsInput(args);
        } else if (t == InputType.RELEASED) {
        	var args = new ControlsArgs(c);
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
    
    public void addOnAnyPressCallback(ControlsCallback callback) {
    	if (callback == null)
    		throw new EngineException("Callback was null");
    	onAnyPressCallbacks.add(callback);
    }
    
    public boolean removeOnAnyPressCallback(ControlsCallback callback) {
    	return onAnyPressCallbacks.remove(callback);
    }
    
    public void addOnAnyReleaseCallback(ControlsCallback callback) {
    	
    	onAnyReleaseCallbacks.add(callback);
    }
    
    public void removeOnAnyReleaseCallback(ControlsCallback callback) {
    	onAnyReleaseCallbacks.remove(callback);
    }
    
    private static void addToMap(Map<Controls, List<ControlsCallback>> map,
    		Controls c, ControlsCallback callback) {
    	
    	if (callback == null)
    		throw new EngineException("Callback was null");
    	List<ControlsCallback> callbacks;
        if (!map.containsKey(c)) {
            callbacks = new ArrayList<>();
            map.put(c, callbacks);
        } else {
            callbacks = map.get(c);
        }
        assert callbacks != null;
        if (callbacks.contains(callback))
        	return;
        callbacks.add(callback);
    }
    
    private static void removeMapFromMap(Map<Controls, List<ControlsCallback>> map,
    		Map<Controls, List<ControlsCallback>> removed) {
    	for (var key : removed.keySet()) {
    		if (map.containsKey(key)) {
    			var l = map.get(key);
    			for (var cb : removed.get(key)) {
    				l.remove(cb);
    			}
    		}
    	}
    }
    
    private static void addMapToMap(Map<Controls, List<ControlsCallback>> map,
    		Map<Controls, List<ControlsCallback>> added) {
    	for (var key : added.keySet()) {
    		for (var cb : added.get(key)) {  			
    			addToMap(map, key, cb);
    		}
    	}
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

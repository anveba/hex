package main.engine.ui;

import java.util.*;

import main.engine.EngineException;
import main.engine.TimeRecord;
import main.engine.graphics.Renderer2D;
import main.engine.input.ControlsArgs;

/**
 * Represents a stack of frames. It is the highest level module of the
 * UI framework. Only the topmost frame is active.
 * @author Andreas
 * @see Frame
 */
public class FrameStack {
	
	private List<Frame> stack = new ArrayList<Frame>();
	private int top;
	
	private static FrameStack instance;
	public static FrameStack getInstance() {
		if (instance == null)
			instance = new FrameStack();
		return instance;
	}
	
	private FrameStack() {
		top = 0;
	}
	
	public void push(Frame e) {
		stack.add(e);
		top++;
	}
	
	public void clear() {
		stack.clear();
		top = 0;
	}
	
	public Frame pop() {
		assert stack.size() == top;
		if (top == 0) {
			throw new EngineException("Attempting to pop empty stack");
		}
		return stack.remove(--top);
	}

	public Frame peek() {
		assert stack.size() == top;
		if (top == 0) return null;
		return stack.get(top-1);
	}
	
	public int size() {
		assert stack.size() == top;
		return stack.size();
	}

	public void pressAt(float x, float y) {
		if (top > 0) {
			stack.get(top - 1).clickDownAt(x, y);
		}
	}
	
	public void clickReleaseAt(float x, float y) {
		if (top > 0) {
			stack.get(top - 1).clickReleaseAt(x, y);
		}
	}
	
	public void hoverAt(float x, float y) {
		if (top > 0) {
			stack.get(top - 1).hoverAt(x, y);
		}
	}
	
	public void processTextInput(char ch) {
		if (top > 0) {
			stack.get(top - 1).processTextInput(ch);
		}
	}
	
	public void processControlsInput(ControlsArgs args) {
		if (top > 0) {
			stack.get(top - 1).processControlsInput(args);
		}
	}
	
	public void update(TimeRecord elapsed) {
		if (top > 0) {
			stack.get(top - 1).update(elapsed);
		}
	}
	
	public void draw(Renderer2D renderer) {
		if (top > 0)
			stack.get(top - 1).draw(renderer);
	}
}

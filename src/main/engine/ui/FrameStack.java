package main.engine.ui;

import java.util.*;

import main.engine.EngineException;
import main.engine.graphics.Renderer2D;

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
	
	public void draw(Renderer2D renderer) {
		if (top > 0)
			stack.get(top - 1).draw(renderer);
	}
	
	public int size() {
		assert stack.size() == top;
		return stack.size();
	}
	
	public void clickAt(float x, float y) {
		if (top > 0) {
			stack.get(top - 1).clickAt(x, y);
		}
	}
	
	public void hoverAt(float x, float y) {
		if (top > 0) {
			stack.get(top - 1).hoverAt(x, y);
		}
	}
}

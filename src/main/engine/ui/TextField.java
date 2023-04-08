package main.engine.ui;

import main.engine.font.BitmapFont;
import main.engine.input.Controls;
import main.engine.input.ControlsArgs;
import main.engine.math.Vector2;
import main.hex.Game;

public class TextField extends Text implements Clickable {
    //TODO: Add cursor on hover
    //TODO: Add cursor blinking
    //TODO: ADD Text left alignment (figure out the with of the text)
    //TODO: Add Rect element for selecting text (if based on text width, no with when no text)

    private boolean isFocused;
    private StringBuilder textString = new StringBuilder();
    private String defaultString;
    
    public TextField(float x, float y, BitmapFont font, String text, float height) {
        super(x, y, font, text, height);
        defaultString = text;
    }

    private void focus() {
    	if(!isFocused) {
            setText(textString.toString());
            isFocused = true;
        }
    }
    
    private void unfocus() {
    	if(isFocused) {
			if (textString.isEmpty())
				setText(defaultString);
            isFocused = false;
        }
    }
    
    @Override
    public void processClick(ClickArgs args) {
    	if (containsPosition(args.getX(), args.getY()))
	        focus();
    	else 
    		unfocus();
    }

    @Override
    public boolean containsPosition(float x, float y) {
        return this.getX() - 1.0f / 2.0f <= x
                && this.getY() - getHeight() / 2.0f <= y
                && this.getX() + 1.0f / 2.0f >= x
                && this.getY() + getHeight() / 2.0f >= y;
    }

    @Override
    public void updateCursorPosition(HoverArgs args) {

    }
    
    @Override
	public void processControlsInput(ControlsArgs args) {
		if (args.getControls() == Controls.ESCAPE)
			unfocus();
	}

	@Override
	public void processTextInput(TextInputArgs args) {
        if (isFocused) {
        
        	if (args.getCharacter() == '\b') {        		
        		if (textString.length() > 0)
        			textString.deleteCharAt(textString.length() - 1);
        	}
        	else
        		textString.append(args.getCharacter());
        	
        	setText(textString.toString());
        }
	}
}

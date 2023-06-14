package main.engine.ui.callback;

/**
 * Immutable.
 * @author Andreas - s214971
 *
 */
public class TextInputArgs {
	private char character;
	
	public TextInputArgs(char character) {
		this.character = character;
	}
	
	public char getCharacter() {
		return character;
	}
}

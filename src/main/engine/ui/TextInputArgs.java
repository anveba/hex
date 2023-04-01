package main.engine.ui;

/**
 * Immutable.
 * @author andreas
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

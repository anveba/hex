package main.engine.ui;

/**
 * Immutable.
 * @author andreas
 *
 */
public class ButtonCallbackArgs {

    private int intArg;
	public ButtonCallbackArgs() {
        intArg = 0;
    }
    public ButtonCallbackArgs(int intArg) {
        this.intArg = intArg;
    }

    public int getIntArg() {
        return intArg;
    }
}

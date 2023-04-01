package main.engine.input;

/**
 * Immutable.
 * @author andreas
 *
 */
public class ControlsArgs {

    private Controls c;

    public ControlsArgs(Controls c) {
        this.c = c;
    }

    public Controls getControls() {
        return c;
    }
}

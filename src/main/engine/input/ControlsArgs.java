package main.engine.input;

/**
 * Immutable.
 * @author Andreas - s214971
 *
 */
public class ControlsArgs {

    private final Controls c;

    public ControlsArgs(Controls c) {
        this.c = c;
    }

    public Controls getControls() {
        return c;
    }

}

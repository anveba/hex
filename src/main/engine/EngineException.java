package main.engine;

/**
 * Exception class for exceptions in the engine component.
 * @author Andreas - s214971
 *
 */
public class EngineException extends RuntimeException {

    public EngineException(Exception ex) {
        super(ex);
    }

    public EngineException(String message) {
        super(message);
    }
}

package main.engine;

public class EngineException extends RuntimeException {

    public EngineException(Exception ex) {
        super(ex);
    }

    public EngineException(String message) {
        super(message);
    }
}

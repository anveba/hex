package main.hex.ai;

public class AIException extends RuntimeException {
    public AIException(Exception ex) {
        super(ex);
    }

    public AIException(String message) {
        super(message);
    }
}
package main.hex.ai;

/*
Author Nikolaj
Exception for just about any AI related exceptions
 */
public class AIException extends RuntimeException {
    public AIException(Exception ex) {
        super(ex);
    }

    public AIException(String message) {
        super(message);
    }
}
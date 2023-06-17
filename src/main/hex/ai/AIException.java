package main.hex.ai;

import main.hex.HexException;

/*
Author Nikolaj
Exception for just about any AI related exceptions
 */
public class AIException extends HexException {
    public AIException(Exception ex) {
        super(ex);
    }

    public AIException(String message) {
        super(message);
    }
}
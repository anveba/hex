package main.hex;

public class HexException extends RuntimeException {
	public HexException(Exception ex) {
        super(ex);
    }

    public HexException(String message) {
        super(message);
    }
}

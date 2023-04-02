package main.hex;

public class HexException extends RuntimeException {
	public HexException(Throwable ex) {
        super(ex);
    }

    public HexException(String message) {
        super(message);
    }
}

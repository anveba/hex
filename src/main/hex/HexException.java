package main.hex;

/**
 * Exception associated with the Hex package.
 * @author Andreas - s214971
 *
 */
public class HexException extends RuntimeException {
	public HexException(Throwable ex) {
        super(ex);
    }

    public HexException(String message) {
        super(message);
    }
}

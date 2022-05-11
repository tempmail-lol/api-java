package lol.tempmail.api.exception;

/**
 * Thrown when the token is expired.
 */
public class TempMailTokenExpiredException extends RuntimeException {
    public TempMailTokenExpiredException() {
        super("TempMail token expired.");
    }
}

package equipment;

/**
 * Exception class for indicating that armor cannot be equipped.
 */
public class InvalidArmorException extends Exception {
    public InvalidArmorException(String errorMessage) {
        super(errorMessage);
    }
}

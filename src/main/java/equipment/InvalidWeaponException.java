package equipment;

/**
 * Exception class for indicating that weapon cannot be equipped.
 */
public class InvalidWeaponException extends Exception {
    public InvalidWeaponException(String errorMessage) {
        super(errorMessage);
    }
}

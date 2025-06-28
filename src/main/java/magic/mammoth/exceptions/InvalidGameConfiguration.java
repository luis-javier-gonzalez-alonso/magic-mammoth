package magic.mammoth.exceptions;

public class InvalidGameConfiguration extends RuntimeException {
    public InvalidGameConfiguration(String message) {
        super(message);
    }
}

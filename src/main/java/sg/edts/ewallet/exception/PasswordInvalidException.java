package sg.edts.ewallet.exception;

public class PasswordInvalidException extends RuntimeException {

    public PasswordInvalidException() {
        super("password invalid");
    }
}

package sg.edts.ewallet.exception;

public class UsernameTakenException extends RuntimeException {

    public UsernameTakenException() {
        super("username taken");
    }
}

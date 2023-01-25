package sg.edts.ewallet.exception;

public class LimitExceededException extends RuntimeException {

    public LimitExceededException() {
        super("transaction limit exceeded");
    }
}

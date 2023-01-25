package sg.edts.ewallet.exception;

public class TopUpExceededException extends RuntimeException {

    public TopUpExceededException() {
        super("max topup exceeded");
    }
}

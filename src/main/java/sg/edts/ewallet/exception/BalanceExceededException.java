package sg.edts.ewallet.exception;

public class BalanceExceededException extends RuntimeException {

    public BalanceExceededException() {
        super("max balance exceeded");
    }
}

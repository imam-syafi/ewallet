package sg.edts.ewallet.exception;

public class NotEnoughBalanceException extends RuntimeException {

    public NotEnoughBalanceException() {
        super("not enough balance");
    }
}

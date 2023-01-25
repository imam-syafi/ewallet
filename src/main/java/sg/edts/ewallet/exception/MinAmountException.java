package sg.edts.ewallet.exception;

public class MinAmountException extends RuntimeException {

    public MinAmountException(String minAmount) {
        super(String.format("minimum trx amount is %s", minAmount));
    }
}

package sg.edts.ewallet.exception;

public class KtpTakenException extends RuntimeException {

    public KtpTakenException() {
        super("ktp has been used by other user");
    }
}

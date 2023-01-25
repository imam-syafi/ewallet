package sg.edts.ewallet.exception;

public class UserBannedException extends RuntimeException {

    public UserBannedException() {
        super("user banned");
    }
}

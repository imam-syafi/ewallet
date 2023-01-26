package sg.edts.ewallet.common;

public class Constant {
    public static final Long MIN_BALANCE = 10_000L;
    public static final Long MAX_BALANCE = 10_000_000L;

    public static final Long MIN_TRANSACTION = 10_000L;
    public static final Long MAX_TRANSACTION_UNVERIFIED_KTP = 1_000_000L;
    public static final Long MAX_TRANSACTION_VERIFIED_KTP = 5_000_000L;

    public static final Long MAX_TOP_UP = 10_000_000L;

    public static final Integer MAX_RETRY = 3;

    public static final Float TAX = 0.125F;
}

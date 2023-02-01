package sg.edts.ewallet.dto;

public interface BalanceSummary {
    String getUsername();

    Double getYesterdayBalance();

    Double getCurrentBalance();

    default boolean hasTrx() {
        return getYesterdayBalance() != null && getCurrentBalance() != null;
    }
}

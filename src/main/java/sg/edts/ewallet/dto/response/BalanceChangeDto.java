package sg.edts.ewallet.dto.response;

public record BalanceChangeDto(String username, String changeInPercentage, String balanceChangeDate) {
}

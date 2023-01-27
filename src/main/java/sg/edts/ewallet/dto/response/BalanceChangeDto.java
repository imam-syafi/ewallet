package sg.edts.ewallet.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record BalanceChangeDto(
        String username,
        String changeInPercentage,
        String balanceChangeDate,
        // @JsonIgnore
        // TODO: Delete this, or uncomment line above this
        String debug
) {
}

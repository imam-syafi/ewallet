package sg.edts.ewallet.dto.response;

import sg.edts.ewallet.entity.TransactionStatus;

public record BalanceSentDto(
        Long trxId,
        String originUsername,
        String destinationUsername,
        Long amount,
        TransactionStatus status
) {
}

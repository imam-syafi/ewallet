package sg.edts.ewallet.dto.response;

import sg.edts.ewallet.entity.TransactionEntity.Status;

public record BalanceSentDto(
        Long trxId,
        String originUsername,
        String destinationUsername,
        Double amount,
        Status status
) {
}

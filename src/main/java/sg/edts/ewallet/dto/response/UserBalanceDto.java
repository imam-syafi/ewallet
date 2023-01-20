package sg.edts.ewallet.dto.response;

import sg.edts.ewallet.entity.UserEntity;

import java.text.NumberFormat;
import java.util.Locale;

import static sg.edts.ewallet.common.Constant.MAX_TRANSACTION_UNVERIFIED_KTP;
import static sg.edts.ewallet.common.Constant.MAX_TRANSACTION_VERIFIED_KTP;

public record UserBalanceDto(String balance, String transactionLimit) {

    public static UserBalanceDto from(UserEntity entity) {
        final Long limit = entity.getKtp() == null
                ? MAX_TRANSACTION_UNVERIFIED_KTP
                : MAX_TRANSACTION_VERIFIED_KTP;

        final NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
        final String balance = formatter.format(entity.getBalance());
        final String transactionLimit = formatter.format(limit);

        return new UserBalanceDto(balance, transactionLimit);
    }
}

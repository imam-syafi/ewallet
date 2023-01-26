package sg.edts.ewallet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edts.ewallet.common.Constant;
import sg.edts.ewallet.dto.request.SendBalanceDto;
import sg.edts.ewallet.dto.request.TopUpDto;
import sg.edts.ewallet.dto.response.BalanceSentDto;
import sg.edts.ewallet.entity.TransactionEntity;
import sg.edts.ewallet.entity.TransactionEntity.Type;
import sg.edts.ewallet.entity.UserEntity;
import sg.edts.ewallet.exception.BalanceExceededException;
import sg.edts.ewallet.exception.LimitExceededException;
import sg.edts.ewallet.exception.MinAmountException;
import sg.edts.ewallet.exception.NotEnoughBalanceException;
import sg.edts.ewallet.exception.PasswordInvalidException;
import sg.edts.ewallet.exception.TopUpExceededException;
import sg.edts.ewallet.exception.UserBannedException;
import sg.edts.ewallet.exception.UserNotFoundException;
import sg.edts.ewallet.repository.TransactionRepository;
import sg.edts.ewallet.repository.UserRepository;

import java.util.List;

import static sg.edts.ewallet.common.Constant.MAX_TRANSACTION_UNVERIFIED_KTP;
import static sg.edts.ewallet.common.Constant.MAX_TRANSACTION_VERIFIED_KTP;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UserRepository userRepository;

    public BalanceSentDto create(SendBalanceDto payload) {
        if (payload.amount() < Constant.MIN_TRANSACTION) {
            // TODO: format this currency
            throw new MinAmountException(Constant.MIN_TRANSACTION.toString());
        }

        UserEntity sender = userRepository
                .findByUsername(payload.username())
                .orElseThrow(UserNotFoundException::new);

        if (sender.isBanned()) {
            throw new UserBannedException();
        }

        if (sender.getPassword().equals(payload.password())) {
            sender.resetFailedRetryCount();
        } else {
            sender.incrementFailedRetryCount();
            userRepository.save(sender);
            throw new PasswordInvalidException();
        }

        final Long limit = sender.getKtp() == null
                ? MAX_TRANSACTION_UNVERIFIED_KTP
                : MAX_TRANSACTION_VERIFIED_KTP;

        if (payload.amount() > limit) {
            throw new LimitExceededException();
        }

        Double senderBalance = sender.getBalance();
        Double senderBalanceAfterTransferOut = senderBalance - payload.amount();
        Double taxAmount = payload.amount() * Constant.TAX;
        Double senderBalanceAfterTax = senderBalanceAfterTransferOut - taxAmount;

        if (senderBalanceAfterTax < Constant.MIN_BALANCE) {
            throw new NotEnoughBalanceException();
        }

        UserEntity receiver = userRepository
                .findByUsername(payload.destinationUsername())
                .orElseThrow(UserNotFoundException::new);

        Double receiverBalance = receiver.getBalance();
        Double receiverBalanceAfterTransferIn = receiverBalance + payload.amount();

        if (receiverBalanceAfterTransferIn > Constant.MAX_BALANCE) {
            throw new BalanceExceededException();
        }

        // Everything checks out, commit to database

        sender.setBalance(senderBalanceAfterTransferOut);
        TransactionEntity transferOut = new TransactionEntity(
                -payload.amount(),
                Type.TRANSFER_OUT,
                senderBalance,
                senderBalanceAfterTransferOut,
                sender
        );

        sender.setBalance(senderBalanceAfterTax);
        TransactionEntity tax = new TransactionEntity(
                -taxAmount,
                Type.TAX,
                senderBalanceAfterTransferOut,
                senderBalanceAfterTax,
                sender
        );

        receiver.setBalance(receiverBalanceAfterTransferIn);
        TransactionEntity transferIn = new TransactionEntity(
                payload.amount(),
                Type.TRANSFER_IN,
                receiverBalance,
                receiverBalanceAfterTransferIn,
                receiver
        );

        transactionRepository.saveAll(List.of(transferOut, tax, transferIn));

        return new BalanceSentDto(
                transferOut.getId(),
                payload.username(),
                payload.destinationUsername(),
                payload.amount(),
                TransactionEntity.Status.SETTLED
        );
    }


    public void topUp(TopUpDto payload) {
        if (payload.amount() > Constant.MAX_TOP_UP) {
            throw new TopUpExceededException();
        }

//        if (payload.amount() < Constant.MIN_TRANSACTION) {
//            // TODO: format this currency
//            throw new MinAmountException(Constant.MIN_TRANSACTION.toString());
//        }

        UserEntity entity = userRepository
                .findByUsername(payload.username())
                .orElseThrow(UserNotFoundException::new);

        if (entity.isBanned()) {
            throw new UserBannedException();
        }

        if (entity.getPassword().equals(payload.password())) {
            entity.resetFailedRetryCount();
        } else {
            entity.incrementFailedRetryCount();
            userRepository.save(entity);
            throw new PasswordInvalidException();
        }

        Double balance = entity.getBalance();
        Double balanceAfterTopUp = balance + payload.amount();

        if (balanceAfterTopUp > Constant.MAX_BALANCE) {
            throw new BalanceExceededException();
        }

        // Everything checks out, commit to database

        entity.setBalance(balanceAfterTopUp);
        TransactionEntity topUpTransaction = new TransactionEntity(
                payload.amount(),
                Type.TOP_UP,
                balance,
                balanceAfterTopUp,
                entity
        );

        transactionRepository.save(topUpTransaction);
    }
}

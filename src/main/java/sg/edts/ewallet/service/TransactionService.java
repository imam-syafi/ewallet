package sg.edts.ewallet.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edts.ewallet.common.Constant;
import sg.edts.ewallet.dto.request.SendBalanceDto;
import sg.edts.ewallet.dto.request.TopUpDto;
import sg.edts.ewallet.dto.response.BalanceSentDto;
import sg.edts.ewallet.entity.TransactionEntity;
import sg.edts.ewallet.entity.TransactionStatus;
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
//@Transactional
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

        if (!sender.getPassword().equals(payload.password())) {
            // TODO: count this
            throw new PasswordInvalidException();
        }

        final Long limit = sender.getKtp() == null
                ? MAX_TRANSACTION_UNVERIFIED_KTP
                : MAX_TRANSACTION_VERIFIED_KTP;

        if (payload.amount() > limit) {
            throw new LimitExceededException();
        }

        if (sender.getBanned()) {
            throw new UserBannedException();
        }

        if (sender.getBalance() - payload.amount() < Constant.MIN_BALANCE) {
            throw new NotEnoughBalanceException();
        }

        UserEntity receiver = userRepository
                .findByUsername(payload.destinationUsername())
                .orElseThrow(UserNotFoundException::new);

        sender.setBalance(sender.getBalance() - payload.amount());
        receiver.setBalance(receiver.getBalance() + payload.amount());

        System.out.println(sender.getBalance());
        System.out.println(receiver.getBalance());

//        userRepository.saveAll(List.of(sender, receiver));

        TransactionEntity sent = new TransactionEntity(
                payload.username(),
                -payload.amount(),
                TransactionStatus.SETTLED
        );
        sent.setUser(sender);

        TransactionEntity received = new TransactionEntity(
                payload.destinationUsername(),
                payload.amount(),
                TransactionStatus.SETTLED
        );
        received.setUser(receiver);

        transactionRepository.saveAll(List.of(sent, received));

        System.out.println(sent.getUser().getBalance());
        System.out.println(received.getUser().getBalance());

        return new BalanceSentDto(
                sent.getId(),
                payload.username(),
                payload.destinationUsername(),
                payload.amount(),
                TransactionStatus.SETTLED
        );

//        return transactionRepository
//                .save(new TransactionEntity(payload.username(), payload.amount(), TransactionStatus.SETTLED, sender))
//                .toBalanceSentDto(payload.destinationUsername());
//                .save(TransactionEntity.from(payload, TransactionType.DEBIT, TransactionStatus.SETTLED))
//                .toBalanceSentDto();
    }

    public void topup(TopUpDto payload) {
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

        if (!entity.getPassword().equals(payload.password())) {
            // TODO: count this
            throw new PasswordInvalidException();
        }

        if (entity.getBanned()) {
            throw new UserBannedException();
        }

        if (entity.getBalance() + payload.amount() > Constant.MAX_BALANCE) {
            throw new BalanceExceededException();
        }

        entity.setBalance(entity.getBalance() + payload.amount());

        userRepository.save(entity);
    }
}

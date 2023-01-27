package sg.edts.ewallet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edts.ewallet.dto.response.BalanceChangeDto;
import sg.edts.ewallet.dto.response.ReportGetDto;
import sg.edts.ewallet.entity.TransactionEntity;
import sg.edts.ewallet.repository.TransactionRepository;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService {

    final TransactionRepository transactionRepository;

    @Autowired
    public ReportService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public ReportGetDto generateReport(LocalDate date) {
        Map<String, List<TransactionEntity>> transactionsGroupByUsername = transactionRepository.findByDate(date)
                .stream()
                .collect(Collectors.groupingBy(transaction -> transaction.getAffectedUser().getUsername()));

        final List<BalanceChangeDto> changes = new ArrayList<>();
        final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        final NumberFormat percentFormatter = NumberFormat.getPercentInstance();
        percentFormatter.setMaximumFractionDigits(2);

        transactionsGroupByUsername.forEach((username, transactions) -> {
            final double balanceBefore = transactions.get(0).getBalanceBefore();

            String percentage;
            String debug;
            if (balanceBefore > 0) {
                final double balanceAfter = transactions.get(transactions.size() - 1).getBalanceAfter();
                percentage = percentFormatter.format((balanceAfter - balanceBefore) / balanceBefore);

                // TODO: Delete this
                debug = String.format("%s of %.2f is %.2f", percentage, balanceBefore, (balanceAfter - balanceBefore));
            } else {
                percentage = "-";

                // TODO: Delete this
                debug = "last balance on previous day = 0";
            }

            changes.add(
                    new BalanceChangeDto(
                            username,
                            percentage,
                            dateFormatter.format(date),
                            debug
                    )
            );
        });

        return new ReportGetDto(changes);
    }
}

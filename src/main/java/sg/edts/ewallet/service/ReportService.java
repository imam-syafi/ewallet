package sg.edts.ewallet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edts.ewallet.dto.response.BalanceChangeDto;
import sg.edts.ewallet.dto.response.ReportGetDto;
import sg.edts.ewallet.entity.UserEntity;
import sg.edts.ewallet.repository.UserRepository;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {

    final UserRepository userRepository;

    @Autowired
    public ReportService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ReportGetDto generateReport(LocalDate date) {
        final List<BalanceChangeDto> changes = new ArrayList<>();
        final String balanceChangeDate = date.format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
        final NumberFormat percentFormatter = NumberFormat.getPercentInstance();
        percentFormatter.setMaximumFractionDigits(2);

        for (final UserEntity entity : userRepository.findAll()) {
            var transactions = entity.getTransactions().stream()
                    .filter(t -> t.getDate().equals(date))
                    .toList();

            String percentage = percentFormatter.format(0);

            // TODO: Delete this
            String debug = "commit no transaction";

            if (transactions.size() > 0) {
                final double balanceBefore = transactions.get(0).getBalanceBefore();

                if (balanceBefore > 0) {
                    final double balanceAfter = transactions.get(transactions.size() - 1).getBalanceAfter();
                    final double diff = (balanceAfter - balanceBefore);
                    percentage = percentFormatter.format(diff / balanceBefore);

                    // TODO: Delete this
                    debug = String.format("then=%.2f, now=%.2f, diff=%.2f, trend=%s", balanceBefore, balanceAfter, diff, percentage);
                } else {
                    percentage = "-";

                    // TODO: Delete this
                    debug = "last balance on previous day = 0";
                }
            }

            changes.add(
                    new BalanceChangeDto(
                            entity.getUsername(),
                            percentage,
                            balanceChangeDate,
                            debug
                    )
            );
        }

        return new ReportGetDto(changes);
    }
}

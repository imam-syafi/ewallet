package sg.edts.ewallet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edts.ewallet.dto.response.BalanceChangeDto;
import sg.edts.ewallet.dto.response.ReportGetDto;
import sg.edts.ewallet.repository.UserRepository;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ReportService {

    final UserRepository userRepository;

    @Autowired
    public ReportService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ReportGetDto generateReport(LocalDate date) {
        final String balanceChangeDate = date.format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
        final NumberFormat percentFormatter = NumberFormat.getPercentInstance();
        percentFormatter.setMaximumFractionDigits(2);

        final List<BalanceChangeDto> changes = userRepository.generateBalanceSummary(date).stream()
                .map(balanceSummary -> {
                    String percentage;

                    // TODO: delete this
                    String debug;

                    if (balanceSummary.hasTrx()) {
                        // to avoid division by zero
                        if (balanceSummary.getYesterdayBalance() > 0) {
                            double diff = balanceSummary.getCurrentBalance() - balanceSummary.getYesterdayBalance();
                            double trend = diff / balanceSummary.getYesterdayBalance();

                            percentage = percentFormatter.format(trend);

                            // TODO: delete this
                            debug = String.format("then=%.2f, now=%.2f, diff=%.2f, trend=%s", balanceSummary.getYesterdayBalance(), balanceSummary.getCurrentBalance(), diff, percentage);
                        } else {
                            percentage = "-";

                            // TODO: delete this
                            debug = "last balance on previous day = 0";
                        }

                    } else {
                        percentage = percentFormatter.format(0);

                        // TODO: delete this
                        debug = "commit no transaction";
                    }

                    return new BalanceChangeDto(
                            balanceSummary.getUsername(),
                            percentage,
                            balanceChangeDate,
                            debug
                    );
                })
                .toList();

        return new ReportGetDto(changes);
    }
}

package sg.edts.ewallet.dto.response;

import java.util.List;

public record ReportGetDto(List<BalanceChangeDto> reportBalanceChangeInPercentage) {
}

package ee.qrent.billing.report.domain;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@SuperBuilder
@Getter
@Setter
public class WeeklyReportCalculationResult {
    private WeeklyReport weeklyReport;
    private Set<Long> transactionIds;
}

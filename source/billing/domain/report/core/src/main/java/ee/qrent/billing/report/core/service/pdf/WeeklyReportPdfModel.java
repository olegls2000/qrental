package ee.qrent.billing.report.core.service.pdf;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class WeeklyReportPdfModel {
    private final String firstName;
    private final String lastName;
    private final  Long taxNumber;

    private final Integer callSign;
    private final BigDecimal amount;
    private final BigDecimal feeAmountSunday;

    private final LocalDate currentWeekStart;
    private final LocalDate currentWeekEnd;
    private final LocalDate previousWeekStart;
    private final LocalDate previousWeekEnd;

}

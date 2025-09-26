package ee.qrent.billing.report.core.service.pdf;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@SuperBuilder
public class WeeklyReportPdfInsuranceCase {
  private final LocalDate occurrenceDate;
  private final BigDecimal damageRemaining;
  private final String carRegNumber;
}

package ee.qrent.billing.report.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@SuperBuilder
@Getter
@Setter
public class WeeklyReportInsuranceCase {
  private LocalDate occurrenceDate;
  private String carRegNumber;
  private BigDecimal damageRemaining;
}

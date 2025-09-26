package ee.qrent.billing.report.persistence.entity.jakarta;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeeklyReportInsuranceCaseJakarta {
  private LocalDate occurrenceDate;
  private String carRegNumber;
  private BigDecimal damageRemaining;
}

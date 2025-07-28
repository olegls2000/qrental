package ee.qrent.billing.report.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@SuperBuilder
@Getter
@Setter
public class WeeklyReport {
  private Long id;
  private Long qWeekId;
  private Long driverId;
  private Long callSignId;
  private WeeklyReportType type;
  private Long carId;
  private LocalDate startDate;
  private LocalDate endDate;
  private Long weeksCountTillEnd;
  private BigDecimal depositObligation;
  private BigDecimal depositPaid;
  private WeeklyReportObligationStatus obligationStatus;
  private BigDecimal balanceAmountSunday;
  private BigDecimal balanceAmountAtCalculationMoment;
  private String comment;
}

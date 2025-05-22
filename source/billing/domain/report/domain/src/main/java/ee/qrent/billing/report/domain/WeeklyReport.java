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
  private Integer callSignId;
  private String carId;
  private LocalDate startDate;
  private LocalDate endDate;
  private Long qFirmId;
  private BigDecimal deposit;
  private BigDecimal paidDeposit;
  private WeeklyReportObligationStatus status;
  private BigDecimal balanceAmount;
  private WeeklyReportDetail detail;
  private String comment;
}

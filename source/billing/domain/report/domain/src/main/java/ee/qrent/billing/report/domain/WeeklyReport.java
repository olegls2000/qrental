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
  private Long carId;
  private Long qFirmId;
  private LocalDate startDate;
  private LocalDate endDate;

  private Integer weeksCountTillEnd;
  //TODO: so far not implemented, required special operation: DepositReplenish, will be calculated ion demand by Driver
  private BigDecimal depositObligation;//500
  private BigDecimal depositPaid; //if paid 200, then left over is 300


  private WeeklyReportObligationStatus status;
  private BigDecimal balanceAmountSunday;
  private BigDecimal balanceAmountMonday; //after Monday morning Calculations, calculations MUST be completed
  private String comment;
}

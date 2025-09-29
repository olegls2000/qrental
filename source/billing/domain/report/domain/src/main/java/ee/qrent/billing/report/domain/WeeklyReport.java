package ee.qrent.billing.report.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
  private BigDecimal currentObligationAmount;
  private BigDecimal netAmountOnThursday;
  private BigDecimal balanceAmountAtCalculationMoment;
  private BigDecimal feeAmountSunday;
  private BigDecimal feeAmountAtCalculationMoment;
  private Map<String, BigDecimal> transactionTypesVsAmount;
  private List<WeeklyReportInsuranceCase> insuranceCases;
  private String comment;
}

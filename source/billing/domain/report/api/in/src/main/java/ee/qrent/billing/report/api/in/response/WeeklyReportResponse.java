package ee.qrent.billing.report.api.in.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class WeeklyReportResponse {
  private Long id;
  private String driverName;
  private Long driverTaxNumber;
  private Integer callSign;
  private String carRegistrationNumber;
  private Integer weekYear;
  private Integer weekNumber;
  private LocalDate startDate;
  private LocalDate endDate;
  private String qFirmName;
  private Long weeksCountTillEnd;
  private BigDecimal depositObligation;
  private BigDecimal depositPaid;
  private String obligationStatus;
  private BigDecimal balanceAmountSunday;
  private BigDecimal balanceAmountAtCalculationMoment;
  private String comment;
}

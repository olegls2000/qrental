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
  private final Long taxNumber;

  private final Integer callSign;
  private final BigDecimal
      amount; // total or main amount shown in header; fallback to balance at calculation moment
  private final BigDecimal feeAmountSunday;
  private final String carRegistrationNumber;
  private final BigDecimal depositObligation;
  private final BigDecimal depositPaid;
  private final BigDecimal balanceAmountSunday;
  private final BigDecimal balanceAmountAtCalculationMoment;
  private final Long weeksCountTillEnd;
  private final String obligationStatus;
  private Map<String, BigDecimal> transactionTypesVsAmount;
  private final String comment;

  private final LocalDate currentWeekStart;
  private final LocalDate currentWeekEnd;
  private final LocalDate previousWeekStart;
  private final LocalDate previousWeekEnd;
}

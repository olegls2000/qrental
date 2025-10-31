package ee.qrent.billing.report.core.service.pdf;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class WeeklyReportPdfModel {
  private final String firstName;
  private final String lastName;
  private final Long idNumber;
  private final String language;
  private final Integer reportedWeekNumber;
  private final Integer callSign;
  private final String qFirmName;
  private final String qFirmIban;
  private final String qFirmContact;
  private final BigDecimal amount;
  private final BigDecimal feeAmountSunday;
  private final String carRegistrationNumber;
  private final BigDecimal depositObligation;
  private final BigDecimal depositPaid;
  private final BigDecimal balanceAmountSunday;
  private final BigDecimal debtAmountSunday;
  private final BigDecimal balanceAmountAtCalculationMoment;
  private final BigDecimal feeAmountAtCalculationMoment;
  private final Long weeksCountTillEnd;
  private final String obligationStatus;
  private final BigDecimal currentObligationAmount;
  private final BigDecimal netAmountOnThursday;
  private final BigDecimal totalRentAmount;
  private final BigDecimal incomeTotal;
  private final BigDecimal incomeBolt;
  private final BigDecimal incomeOthers;
  private final BigDecimal totalOtherPaymentAmount;
  private Map<String, BigDecimal> transactionTypesVsAmount;
  private List<WeeklyReportPdfInsuranceCase> insuranceCases;
  private final LocalDate currentWeekStart;
  private final LocalDate currentWeekEnd;
  private final LocalDate previousWeekStart;
  private final BigDecimal distributedObligationAmount;
  private final LocalDate previousWeekEnd;
  private final LocalDate nextWeekStart;
  private final LocalDate nextWeekEnd;
  private final BigDecimal totalPaymentAmount;
  private final String comment;
}

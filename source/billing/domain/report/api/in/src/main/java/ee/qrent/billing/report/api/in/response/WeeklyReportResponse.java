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
  private BigDecimal deposit;
  private BigDecimal paidDeposit;
  private String status;
  private BigDecimal balanceAmount;
  private BigDecimal obligationTotal;
  private BigDecimal obligationRent;
  private BigDecimal obligationDebt;
  private BigDecimal obligationRepairment;
  private BigDecimal obligationRepairmentFranchise;
  private BigDecimal obligationOthers;
  private BigDecimal obligationFee;
  private BigDecimal bonusNewDriver;
  private BigDecimal bonusReliablePartner;
  private BigDecimal bonusBolt;
  private BigDecimal bonusFriend;
  private BigDecimal obligationRentAdjustmentBolt;
  private BigDecimal obligationRentAdjustmentForus;
  private BigDecimal prepayment;
  private String comment;
}

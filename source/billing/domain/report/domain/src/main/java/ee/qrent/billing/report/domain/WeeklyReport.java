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
  private BigDecimal deposit;
  private BigDecimal paidDeposit;
  private WeeklyReportObligationStatus status;
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

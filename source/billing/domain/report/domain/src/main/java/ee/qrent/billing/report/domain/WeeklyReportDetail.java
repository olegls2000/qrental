package ee.qrent.billing.report.domain;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import java.math.BigDecimal;

@SuperBuilder
@Getter
public class WeeklyReportDetail {
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
}

package ee.qrent.billing.report.persistence.entity.jakarta;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.*;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "weekly_report")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WeeklyReportJakartaEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "q_week_id")
  private Long qWeekId;

  @Column(name = "driver_id")
  private Long driverId;

  @Column(name = "call_sign_id")
  private Long callSignId;

  @Column(name = "car_id")
  private Long carId;

  @Column(name = "q_firm_id")
  private Long qFirmId;

  @Column(name = "obligation_status")
  @Enumerated(EnumType.STRING)
  private WeeklyReportObligationStatusJakarta obligationStatus;

  @Column(name = "obligation_total")
  private BigDecimal obligationTotal;

  @Column(name = "obligation_rent")
  private BigDecimal obligationRent;

  @Column(name = "obligation_debt")
  private BigDecimal obligationDebt;

  @Column(name = "obligation_repairment")
  private BigDecimal obligationRepairment;

  @Column(name = "obligation_repairment_franchise")
  private BigDecimal obligationRepairmentFranchise;

  @Column(name = "obligation_others")
  private BigDecimal obligationOthers;

  @Column(name = "obligation_fee")
  private BigDecimal obligationFee;

  @Column(name = "bonus_new_driver")
  private BigDecimal bonusNewDriver;

  @Column(name = "bonus_reliable_partner")
  private BigDecimal bonusReliablePartner;

  @Column(name = "bonus_bolt")
  private BigDecimal bonusBolt;

  @Column(name = "bonus_friend")
  private BigDecimal bonusFriend;

  @Column(name = "obligation_adjustment_bolt")
  private BigDecimal obligationRentAdjustmentBolt;

  @Column(name = "obligation_adjustment_forus")
  private BigDecimal obligationRentAdjustmentForus;

  @Column(name = "prepayment")
  private BigDecimal prepayment;

  @Column(name = "comment")
  private String comment;
}

package ee.qrent.billing.report.persistence.entity.jakarta;

import static jakarta.persistence.GenerationType.IDENTITY;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

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

  @Column(name = "weeks_count_till_end")
  private Long weeksCountTillEnd;

  @Column(name = "deposit_obligation")
  private BigDecimal depositObligation;

  @Column(name = "deposit_paid")
  private BigDecimal depositPaid;

  @Column(name = "type")
  @Enumerated(EnumType.STRING)
  private WeeklyReportTypeJakarta type;

  @Column(name = "obligation_status")
  @Enumerated(EnumType.STRING)
  private WeeklyReportObligationStatusJakarta obligationStatus;

  @Column(name = "balance_amount_sunday")
  private BigDecimal balanceAmountSunday;

  @Column(name = "fee_amount_sunday")
  private BigDecimal feeAmountSunday;

  @Column(name = "balance_Amount_at_calculation_moment")
  private BigDecimal balanceAmountAtCalculationMoment;

  @Type(JsonBinaryType.class)
  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "transaction_types_vs_amount", columnDefinition = "jsonb")
  private Map<String, BigDecimal> transactionTypesVsAmount;

  @Column(name = "comment")
  private String comment;
}

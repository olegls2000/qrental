package ee.qrental.transaction.entity.jakarta.balance;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "balance")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BalanceJakartaEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "driver_id")
  private Long driverId;

  @Column(name = "created")
  private LocalDate created;

  @Column(name = "fee_able_amount")
  private BigDecimal feeAbleAmount;

  @Column(name = "non_fee_able_amount")
  private BigDecimal nonFeeAbleAmount;

  @Column(name = "positive_amount")
  private BigDecimal positiveAmount;

  @Column(name = "fee_amount")
  private BigDecimal feeAmount;

  @Column(name = "repairment_amount")
  private BigDecimal repairmentAmount;

  @Column(name = "q_week_id")
  private Long qWeekId;

  @Column(name = "derived")
  private Boolean derived;
}

package ee.qrent.billing.insurance.persistence.entity.jakarta;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.*;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "insurance_case_balance")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InsuranceCaseBalanceJakartaEntity {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "q_week_id")
  private Long qWeekId;

  @Column(name = "damage_remaining")
  private BigDecimal damageRemaining;

  @Column(name = "self_responsibility_remaining")
  private BigDecimal selfResponsibilityRemaining;

  @ManyToOne
  @JoinColumn(name = "insurance_case_id")
  private InsuranceCaseJakartaEntity insuranceCase;

  @Column(name = "with_q_kasko")
  private Boolean withQKasko;

  @ManyToOne
  @JoinColumn(name = "insurance_calculation_id")
  private InsuranceCalculationJakartaEntity insuranceCalculation;
}

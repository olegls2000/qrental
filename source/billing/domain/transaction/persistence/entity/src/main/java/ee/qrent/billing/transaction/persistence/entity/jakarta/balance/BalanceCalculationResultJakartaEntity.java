package ee.qrent.billing.transaction.persistence.entity.jakarta.balance;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "balance_calculation_result")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BalanceCalculationResultJakartaEntity {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "calculation_id")
  private BalanceCalculationJakartaEntity calculation;

  @ManyToOne
  @JoinColumn(name = "balance_id")
  private BalanceJakartaEntity balance;
}

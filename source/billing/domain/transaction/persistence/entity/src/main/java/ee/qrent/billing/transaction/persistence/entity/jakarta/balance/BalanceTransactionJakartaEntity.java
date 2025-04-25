package ee.qrent.billing.transaction.persistence.entity.jakarta.balance;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "balance_transaction")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BalanceTransactionJakartaEntity {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "transaction_id")
  private Long transactionId;

  @ManyToOne
  @JoinColumn(name = "balance_id")
  private BalanceJakartaEntity balance;
}

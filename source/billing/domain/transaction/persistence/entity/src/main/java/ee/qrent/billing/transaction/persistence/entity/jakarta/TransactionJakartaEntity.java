package ee.qrent.billing.transaction.persistence.entity.jakarta;

import static jakarta.persistence.GenerationType.IDENTITY;

import ee.qrent.billing.transaction.persistence.entity.jakarta.type.TransactionTypeJakartaEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "transaction")
@Audited
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TransactionJakartaEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "driver_id")
  private Long driverId;

  @Column(name = "amount")
  private BigDecimal amount;

  @Column(name = "date")
  private LocalDate date;

  @Column(name = "comment")
  private String comment;

  @ManyToOne
  @JoinColumn(name = "transaction_type_id", nullable = false)
  private TransactionTypeJakartaEntity type;
}

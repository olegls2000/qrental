package ee.qrental.invoice.entity.jakarta;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "transaction_per_invoice")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InvoiceTransactionJakartaEntity {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "transaction_id")
  private Long transactionId;

  @ManyToOne
  @JoinColumn(name = "invoice_id")
  private InvoiceJakartaEntity invoice;
}

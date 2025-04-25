package ee.qrent.billing.invoice.persistence.entity.jakarta;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "invoice_calculation_result")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InvoiceCalculationResultJakartaEntity {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "calculation_id")
  private InvoiceCalculationJakartaEntity calculation;

  @ManyToOne
  @JoinColumn(name = "invoice_id")
  private InvoiceJakartaEntity invoice;
}

package ee.qrental.invoice.entity.jakarta.calculation;

import static jakarta.persistence.GenerationType.IDENTITY;

import ee.qrental.invoice.entity.jakarta.InvoiceJakartaEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "invoice_calculation_result")
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

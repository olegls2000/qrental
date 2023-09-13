package ee.qrental.invoice.entity.jakarta;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "invoice_item")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InvoiceItemJakartaEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "amount")
  private BigDecimal amount;

  @Column(name = "type")
  private String type;

  @Column(name = "description")
  private String description;

  @ManyToOne
  @JoinColumn(name = "invoice_id")
  private InvoiceJakartaEntity invoice;
}

package ee.qrental.transaction.entity.jakarta.type;

import static jakarta.persistence.GenerationType.IDENTITY;

import ee.qrental.transaction.entity.jakarta.kind.TransactionKindJakartaEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "transaction_type")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TransactionTypeJakartaEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  @Column(name = "invoice_name")
  private String invoiceName;

  @Column(name = "invoice_included")
  private Boolean invoiceIncluded;

  @Column(name = "comment")
  private String comment;

  @ManyToOne
  @JoinColumn(name = "transaction_kind_id")
  private TransactionKindJakartaEntity kind;
}

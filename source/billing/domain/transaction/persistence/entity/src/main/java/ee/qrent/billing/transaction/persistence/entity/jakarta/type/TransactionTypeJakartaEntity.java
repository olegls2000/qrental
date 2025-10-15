package ee.qrent.billing.transaction.persistence.entity.jakarta.type;

import static jakarta.persistence.GenerationType.IDENTITY;

import ee.qrent.billing.transaction.persistence.entity.jakarta.kind.TransactionKindJakartaEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "transaction_type")
//@Audited
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TransactionTypeJakartaEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "code")
  private String code;

  @Column(name = "name_eng")
  private String nameEng;

  @Column(name = "name_rus")
  private String nameRus;

  @Column(name = "name_est")
  private String nameEst;

  @Column(name = "invoice_included")
  private Boolean invoiceIncluded;

  @Column(name = "ui_visible")
  private Boolean uiVisible;

    @Column(name = "ui_name")
    private String uiName;

  @Column(name = "comment")
  private String comment;

  @ManyToOne
  @JoinColumn(name = "transaction_kind_id")
  private TransactionKindJakartaEntity kind;
}

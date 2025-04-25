package ee.qrent.billing.transaction.persistence.entity.jakarta.kind;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "transaction_kind")
@Audited
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TransactionKindJakartaEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "code")
  private String code;

  @Column(name = "name")
  private String name;

  @Column(name = "comment")
  private String comment;
}

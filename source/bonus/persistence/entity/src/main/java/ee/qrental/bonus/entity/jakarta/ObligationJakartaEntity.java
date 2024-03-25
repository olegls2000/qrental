package ee.qrental.bonus.entity.jakarta;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "obligation")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ObligationJakartaEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "q_week_id")
  private Long qWeekId;

  @Column(name = "driver_id")
  private Long driverId;

  @Column(name = "obligation_amount")
  private BigDecimal obligationAmount;

  @Column(name = "positive_amount")
  private BigDecimal positiveAmount;

  @Column(name = "match_count")
  private Integer matchCount;

  @Column(name = "comment")
  private String comment;
}

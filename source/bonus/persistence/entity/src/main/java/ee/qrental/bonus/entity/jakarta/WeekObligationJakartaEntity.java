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
@Table(name = "week_obligation")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WeekObligationJakartaEntity {

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

  @Column(name = "comment")
  private String comment;
}

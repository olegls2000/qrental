package ee.qrental.bonus.entity.jakarta;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "obligation_calculation")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
public class ObligationCalculationJakartaEntity {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "action_date")
  private LocalDate actionDate;

  @Column(name = "q_week_id")
  private Long qWeekId;

  @Column(name = "comment")
  private String comment;

  @OneToMany(mappedBy = "obligationCalculation")
  private List<ObligationCalculationResultJakartaEntity> results;
}

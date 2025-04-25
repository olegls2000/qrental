package ee.qrent.billing.transaction.persistence.entity.jakarta.balance;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "balance_calculation")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BalanceCalculationJakartaEntity {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "action_date")
  private LocalDate actionDate;

  //TODO change to qWeekId
  @Column(name = "start_date")
  private LocalDate startDate;

  @Column(name = "end_date")
  private LocalDate endDate;

  @Column(name = "comment")
  private String comment;

  @OneToMany(mappedBy = "calculation")
  private List<BalanceCalculationResultJakartaEntity> results;
}

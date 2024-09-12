package ee.qrental.insurance.entity.jakarta;

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
@Table(name = "insurance_calculation")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InsuranceCalculationJakartaEntity {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "action_date")
  private LocalDate actionDate;

  @Column(name = "q_week_id")
  private Long qWeekId;

  @Column(name = "comment")
  private String comment;

  @OneToMany(mappedBy = "insuranceCalculation")
  private List<InsuranceCaseBalanceJakartaEntity> insuranceCaseBalances;
}

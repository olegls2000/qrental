package ee.qrent.billing.insurance.persistence.entity.jakarta;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "insurance_case")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InsuranceCaseJakartaEntity {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "driver_id")
  private Long driverId;

  @Column(name = "car_id")
  private Long carId;

  @Column(name = "occurrence_date")
  private LocalDate occurrenceDate;

  @Column(name = "start_q_week_id")
  private Long startQWeekId;

  @Column(name = "damage_amount")
  private BigDecimal damageAmount;

  @Column(name = "description")
  private String description;

  private Boolean active;

  @OneToMany(mappedBy = "insuranceCase")
  List<InsuranceCaseBalanceJakartaEntity> balances;
}

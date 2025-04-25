package ee.qrent.billing.contract.persistence.entity.jakarta;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "absence")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AbsenceJakartaEntity {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "driver_id")
  private Long driverId;

  @Column(name = "date_start")
  private LocalDate dateStart;

  @Column(name = "date_end")
  private LocalDate dateEnd;

  @Column(name = "with_car")
  private Boolean withCar;

  @Column(name = "reason")
  private String reason;

  @Column(name = "comment")
  private String comment;
}

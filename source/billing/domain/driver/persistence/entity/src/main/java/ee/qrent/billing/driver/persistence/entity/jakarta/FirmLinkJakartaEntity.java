package ee.qrent.billing.driver.persistence.entity.jakarta;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "firm_link")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class FirmLinkJakartaEntity {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "date_start")
  private LocalDate dateStart;

  @Column(name = "date_end")
  private LocalDate dateEnd;

  @Column(name = "comment")
  private String comment;

  @Column(name = "firm_id")
  private Long firmId;

  @ManyToOne
  @JoinColumn(name = "driver_id")
  private DriverJakartaEntity driver;
}

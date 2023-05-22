package ee.qrental.driver.entity.jakarta;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "call_sign_link")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class CallSignLinkJakartaEntity {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "date_start")
  private LocalDate dateStart;

  @Column(name = "date_end")
  private LocalDate dateEnd;

  @Column(name = "comment")
  private String comment;

  @ManyToOne
  @JoinColumn(name = "call_sign_id")
  private CallSignJakartaEntity callSign;

  @ManyToOne
  @JoinColumn(name = "driver_id")
  private DriverJakartaEntity driver;
}

package ee.qrent.billing.contract.persistence.entity.jakarta;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "authorization_bolt")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthorizationJakartaEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "driver_id")
  private Long driverId;

  @Column(name = "driver_isikukood")
  private Long driverIsikukood;

  @Column(name = "driver_first_name")
  private String driverFirstName;

  @Column(name = "driver_last_name")
  private String driverLastName;

  @Column(name = "driver_email")
  private String driverEmail;

  @Column(name = "created")
  private LocalDate created;
}

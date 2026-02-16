package ee.qrent.billing.car.persistence.entity.jakarta;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "branding_verification")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BrandingVerificationJakartaEntity {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "date")
  private LocalDate date;

  @Column(name = "driver_id")
  private Long driverId;

  @Column(name = "car_id")
  private Long carId;

  @Column(name = "car_link_id")
  private Long carLinkId;

  @Column(name = "branding_expiration_date")
  private LocalDate brandingExpirationDate;
}

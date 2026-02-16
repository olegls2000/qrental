package ee.qrent.billing.car.persistence.entity.jakarta;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "branding_verification_calculation_result")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BrandingVerificationCalculationResultJakartaEntity {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "calculation_id")
  private BrandingVerificationCalculationJakartaEntity calculation;

  @ManyToOne
  @JoinColumn(name = "branding_verification_id")
  private BrandingVerificationJakartaEntity verification;
}

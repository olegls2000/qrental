package ee.qrent.billing.car.persistence.entity.jakarta;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "branding_verification_calculation")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BrandingVerificationCalculationJakartaEntity {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "action_date")
  private LocalDate actionDate;

  @Column(name = "comment")
  private String comment;

  @Column(name = "type")
  private String type;

  @OneToMany(mappedBy = "calculation")
  private List<BrandingVerificationCalculationResultJakartaEntity> results;
}

package ee.qrent.billing.transaction.persistence.entity.jakarta.rent;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "rent_calculation_result")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RentCalculationResultJakartaEntity {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "car_link_id")
  private Long carLinkId;

  @Column(name = "transaction_id")
  private Long transactionId;

  @ManyToOne
  @JoinColumn(name = "rent_calculation_id")
  private RentCalculationJakartaEntity rentCalculation;
}

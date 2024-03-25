package ee.qrental.bonus.entity.jakarta;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "obligation_calculation_result")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ObligationCalculationResultJakartaEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "obligation_id")
    private Long obligationId;

    @ManyToOne
    @JoinColumn(name = "obligation_calculation_id")
    private ObligationCalculationJakartaEntity obligationCalculation;
}

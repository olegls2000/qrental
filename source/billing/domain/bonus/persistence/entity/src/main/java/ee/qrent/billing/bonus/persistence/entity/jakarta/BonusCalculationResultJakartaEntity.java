package ee.qrent.billing.bonus.persistence.entity.jakarta;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "bonus_calculation_result")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BonusCalculationResultJakartaEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "transaction_id")
    private Long transactionId;

    @ManyToOne
    @JoinColumn(name = "bonus_program_id")
    private BonusProgramJakartaEntity bonusProgram;

    @ManyToOne
    @JoinColumn(name = "bonus_calculation_id")
    private BonusCalculationJakartaEntity bonusCalculation;
}

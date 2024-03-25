package ee.qrental.bonus.domain;


import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class BonusCalculation {

    private Long id;
    private Long qWeekId;
    private LocalDate actionDate;
    private String comment;
    private List<BonusCalculationResult> results;
}

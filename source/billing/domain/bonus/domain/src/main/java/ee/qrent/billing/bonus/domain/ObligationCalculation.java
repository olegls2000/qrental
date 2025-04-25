package ee.qrent.billing.bonus.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class ObligationCalculation {

    private Long id;
    private Long qWeekId;
    private LocalDate actionDate;
    private String comment;
    private List<ObligationCalculationResult> results;
}

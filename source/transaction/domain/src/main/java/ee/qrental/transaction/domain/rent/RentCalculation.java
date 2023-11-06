package ee.qrental.transaction.domain.rent;

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
public class RentCalculation {

  private Long id;
  private Long qWeekId;
  private LocalDate actionDate;
  private String comment;
  private List<RentCalculationResult> results;
}

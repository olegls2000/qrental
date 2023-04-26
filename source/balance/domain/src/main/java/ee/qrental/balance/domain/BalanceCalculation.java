package ee.qrental.balance.domain;

import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public class BalanceCalculation {
  private Long id;
  private LocalDate actionDate;
  private List<BalanceCalculationResult> results;
  private String comment;
}

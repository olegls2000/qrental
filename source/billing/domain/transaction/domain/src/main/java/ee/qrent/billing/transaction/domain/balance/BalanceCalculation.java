package ee.qrent.billing.transaction.domain.balance;

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
  private LocalDate startDate;
  private LocalDate endDate;
  private LocalDate actionDate;
  private List<BalanceCalculationResult> results;
  private String comment;
}

package ee.qrental.transaction.api.in.response.balance;

import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class BalanceCalculationResponse {
  private Long id;
  private LocalDate startDate;
  private LocalDate endDate;
  private LocalDate actionDate;
  private List<BalanceResponse> balances;
  private String comment;
}

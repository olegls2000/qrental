package ee.qrental.balance.api.in.response;

import java.time.LocalDate;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class BalanceCalculationResponse {
  private Long id;
  private LocalDate actionDate;
  private String comment;
}

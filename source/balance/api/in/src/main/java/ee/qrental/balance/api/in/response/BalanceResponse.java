package ee.qrental.balance.api.in.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class BalanceResponse {
  private Long id;
  private BigDecimal amount;
  private Integer weekNumber;
  private Long driverId;
  private LocalDate created;
}

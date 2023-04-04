package ee.qrental.transaction.api.in.response.balance;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class BalanceResponse {
  private Long driverId;
  private Integer callSign;
  private String firstName;
  private String lastName;
  private BigDecimal total;
}

package ee.qrent.billing.transaction.api.in.response.balance;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class BalanceAmountWithDriverResponse {
  private BigDecimal total;
  private Long driverId;
  private Integer callSign;
  private String firstName;
  private String lastName;
  private String phone;
}

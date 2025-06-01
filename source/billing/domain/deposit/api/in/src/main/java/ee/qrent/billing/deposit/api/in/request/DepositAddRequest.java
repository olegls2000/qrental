package ee.qrent.billing.deposit.api.in.request;

import ee.qrent.common.in.request.AbstractAddRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class DepositAddRequest extends AbstractAddRequest {
  private Long driverId;
  private BigDecimal amount;
  private String comment;
}

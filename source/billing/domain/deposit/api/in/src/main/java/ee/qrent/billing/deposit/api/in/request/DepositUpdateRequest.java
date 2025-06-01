package ee.qrent.billing.deposit.api.in.request;

import ee.qrent.common.in.request.AbstractUpdateRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class DepositUpdateRequest extends AbstractUpdateRequest {
  private BigDecimal amount;
  private Long driverId;
  private LocalDate createdOn;
  private String comment;
}

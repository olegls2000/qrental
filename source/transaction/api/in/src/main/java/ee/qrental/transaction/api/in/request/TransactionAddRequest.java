package ee.qrental.transaction.api.in.request;

import ee.qrent.common.in.request.AbstractAddRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TransactionAddRequest extends AbstractAddRequest {
  private Long transactionTypeId;
  private Long driverId;
  private BigDecimal amount;
  private Integer weekNumber;
  private LocalDate date = LocalDate.now();
  private String comment;
}

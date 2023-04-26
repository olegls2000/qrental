package ee.qrental.balance.api.in.request;

import java.time.LocalDate;
import ee.qrental.common.core.in.request.AbstractAddRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class BalanceCalculationAddRequest extends AbstractAddRequest {

  private LocalDate actionDate = LocalDate.now();
  private String comment;
}

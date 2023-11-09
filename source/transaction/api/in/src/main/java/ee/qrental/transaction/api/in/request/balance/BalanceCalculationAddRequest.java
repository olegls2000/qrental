package ee.qrental.transaction.api.in.request.balance;

import ee.qrental.common.core.in.request.AbstractAddRequest;
import ee.qrental.common.core.utils.QWeek;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class BalanceCalculationAddRequest extends AbstractAddRequest {
  private LocalDate actionDate = LocalDate.now();
  private Long qWeekId;
  private String comment;
}

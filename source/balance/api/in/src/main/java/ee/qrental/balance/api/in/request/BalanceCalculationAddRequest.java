package ee.qrental.balance.api.in.request;

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
  private Integer lastYear;
  private QWeek lastWeek;
  private String comment;
}

package ee.qrental.transaction.api.in.request;

import ee.qrental.common.core.utils.QWeek;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class TransactionWeekFilterRequest {
  private Integer year;
  private QWeek week;
}

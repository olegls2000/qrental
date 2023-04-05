package ee.qrental.transaction.api.in.query.filter;

import ee.qrental.common.core.utils.QWeek;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class YearAndWeekFilter {
  private Integer year;
  private QWeek week;
}

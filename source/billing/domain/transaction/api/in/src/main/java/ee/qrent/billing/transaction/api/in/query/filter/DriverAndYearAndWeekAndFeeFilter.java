package ee.qrent.billing.transaction.api.in.query.filter;

import ee.qrent.common.utils.QWeek;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Setter
@Getter
@ToString
public class DriverAndYearAndWeekAndFeeFilter {
    private Long driverId;
    private Integer year;
    private QWeek week;
  private FeeOption feeOption = FeeOption.WITH_FEE;
}

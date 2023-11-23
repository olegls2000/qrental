package ee.qrental.transaction.api.in.query.filter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public class WeekAndDriverFilter {
  private Long qWeekId;
  private Long driverId;
  private FeeOption feeOption = FeeOption.WITH_FEE;
}

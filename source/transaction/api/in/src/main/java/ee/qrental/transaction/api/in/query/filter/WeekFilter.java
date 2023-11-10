package ee.qrental.transaction.api.in.query.filter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public class WeekFilter {
  private Long qWeekId;
}

package ee.qrent.billing.report.api.in.request;

import ee.qrent.common.in.request.AbstractAddRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public class WeeklyReportAddRequest extends AbstractAddRequest {
  private Long qWeekId;

  private WeeklyReportType type;
}

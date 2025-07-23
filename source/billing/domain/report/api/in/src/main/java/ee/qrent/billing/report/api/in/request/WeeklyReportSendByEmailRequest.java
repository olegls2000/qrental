package ee.qrent.billing.report.api.in.request;

import ee.qrent.common.in.request.AbstractAddRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WeeklyReportSendByEmailRequest extends AbstractAddRequest {
  private Long id;
}

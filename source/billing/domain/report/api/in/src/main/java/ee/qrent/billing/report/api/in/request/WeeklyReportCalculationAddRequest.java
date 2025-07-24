package ee.qrent.billing.report.api.in.request;

import ee.qrent.common.in.request.AbstractAddRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class WeeklyReportCalculationAddRequest extends AbstractAddRequest {
  private LocalDate actionDate = LocalDate.now();
  private Long qWeekId;
  private WeeklyReportTypeIn type;
  private String comment;
}

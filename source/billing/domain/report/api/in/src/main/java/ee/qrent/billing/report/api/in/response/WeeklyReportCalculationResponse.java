package ee.qrent.billing.report.api.in.response;

import java.time.LocalDate;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class WeeklyReportCalculationResponse {
  private Long id;
  private Integer reportsCount;
  private String type;
  private Integer year;
  private Integer weekNumber;
  private LocalDate dateStart;
  private LocalDate dateEnd;
  private LocalDate actionDate;
  private String comment;
}

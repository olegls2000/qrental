package ee.qrent.billing.bonus.api.in.response;

import java.time.LocalDate;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class ObligationCalculationResponse {
  private Long id;
  private Integer year;
  private Integer weekNumber;
  private LocalDate startDate;
  private LocalDate endDate;
  private Integer obligationsCount;
  private LocalDate actionDate;
  private String comment;
}

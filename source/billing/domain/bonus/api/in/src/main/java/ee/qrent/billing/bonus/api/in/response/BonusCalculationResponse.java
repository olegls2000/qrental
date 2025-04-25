package ee.qrent.billing.bonus.api.in.response;

import java.time.LocalDate;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class BonusCalculationResponse {
  private Long id;
  private Integer year;
  private Integer weekNumber;
  private LocalDate startDate;
  private LocalDate endDate;
  private Integer transactionsCount;
  private LocalDate actionDate;
  private String comment;
}

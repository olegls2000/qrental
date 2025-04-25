package ee.qrent.billing.transaction.api.in.response.rent;

import java.time.LocalDate;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class RentCalculationResponse {
  private Long id;
  private Integer year;
  private Integer weekNumber;
  private LocalDate startDate;
  private LocalDate endDate;
  private Integer transactionCount;
  private LocalDate actionDate;
  private String comment;
}

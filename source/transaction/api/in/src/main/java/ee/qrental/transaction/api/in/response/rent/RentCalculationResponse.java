package ee.qrental.transaction.api.in.response.rent;

import java.time.LocalDate;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class RentCalculationResponse {
  private Long id;
  private LocalDate actionDate;
  private LocalDate startDate;
  private LocalDate endDate;
  private Integer transactionCount;
  private String comment;
}

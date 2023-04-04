package ee.qrental.transaction.api.in.response;

import java.time.LocalDate;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class TransactionDetailResponse {
  private Long id;
  private String typeId;
  private String driverId;
  private Long amount;
  private Integer weekNumber;
  private LocalDate date;
  private String comment;
}

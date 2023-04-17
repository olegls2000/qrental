package ee.qrental.transaction.api.in.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class TransactionResponse {
  private Long id;
  private String type;
  private String driverInfo;
  private Long driverId;
  private Integer callSign;
  private BigDecimal realAmount;
  private Integer weekNumber;
  private LocalDate date;
  private Boolean withVat;
  private String comment;
}

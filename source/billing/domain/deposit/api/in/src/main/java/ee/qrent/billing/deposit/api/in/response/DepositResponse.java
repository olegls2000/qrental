package ee.qrent.billing.deposit.api.in.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@SuperBuilder
@Getter
public class DepositResponse {
  private Long id;
  private BigDecimal amount;
  private String driverFirstName;
  private String driverLastName;
  private Long driverTaxNumber;
  private LocalDate createdOn;
  private String comment;
}

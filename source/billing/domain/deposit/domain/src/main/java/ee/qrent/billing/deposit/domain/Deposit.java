package ee.qrent.billing.deposit.domain;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@SuperBuilder
@Getter
public class Deposit {
  private Long id;
  private Long driverId;
  private BigDecimal amount;
  private LocalDate createdOn;
  private String comment;
}

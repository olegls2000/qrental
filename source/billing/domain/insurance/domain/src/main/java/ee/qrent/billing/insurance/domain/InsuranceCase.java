package ee.qrent.billing.insurance.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@SuperBuilder
@Getter
@Setter
public class InsuranceCase {
  private Long id;
  private Long driverId;
  private Long carId;
  private LocalDate occurrenceDate;
  private Long startQWeekId;
  private BigDecimal damageAmount;
  private Boolean active;
  private String description;
}

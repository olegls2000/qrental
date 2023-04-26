package ee.qrental.balance.domain;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public class Balance {
  private Long id;
  private Integer weekNumber;
  private Long driverId;
  private LocalDate created;
}

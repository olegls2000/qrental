package ee.qrent.billing.driver.domain;

import java.time.LocalDate;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class Friendship {
  private static final int FRIENDSHIP_DAYS_COUNT = 28;

  private Long id;
  private Long driverId;
  private Long friendId;
  private LocalDate startDate;
}

package ee.qrental.driver.domain;

import static ee.qrental.common.core.utils.QTimeUtils.getLastDayOfWeekInYear;
import static ee.qrental.common.core.utils.QTimeUtils.getWeekNumber;

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

  public boolean isUpdatable() {
    final var weekNumber = getWeekNumber(startDate);
    final var year = startDate.getYear();
    final var lastDayAvailableForUpdate = getLastDayOfWeekInYear(year, weekNumber);
    return lastDayAvailableForUpdate.isAfter(LocalDate.now());
  }
}

package ee.qrent.common.utils;

import java.time.LocalDate;
import lombok.Getter;
import org.threeten.extra.YearWeek;

public class QWeekIterator {

  @Getter private final LocalDate endPeriod;
  @Getter private LocalDate startPeriod;

  public QWeekIterator(LocalDate startPeriod, LocalDate endPeriod) {
    this.startPeriod = startPeriod;
    this.endPeriod = endPeriod;
  }

  public boolean hasNext() {
    return startPeriod.isBefore(endPeriod);
  }

  public Week next() {
    LocalDate currentWeekStart;
    LocalDate currentWeekEnd;

    final var yearWeekStartPeriod = YearWeek.from(startPeriod);
    final var year = yearWeekStartPeriod.getYear();
    final var currentWeekNumber = yearWeekStartPeriod.getWeek();
    currentWeekStart = startPeriod;
    currentWeekEnd = calculateWeekEndDate(year, currentWeekNumber);

    final var week = new Week(year, currentWeekNumber, currentWeekStart, currentWeekEnd);

    moveStartPeriodToTheNextWeek(year, currentWeekNumber);

    return week;
  }

  private LocalDate calculateWeekEndDate(final int year, final int weekNumber) {
    final var lastDayOfWeek = QTimeUtils.getLastDayOfWeekInYear(year, weekNumber);
    if (this.endPeriod.isBefore(lastDayOfWeek)) {

      return this.endPeriod;
    }

    return lastDayOfWeek;
  }

  private void moveStartPeriodToTheNextWeek(final int year, final int weekNumber) {
    final var nextWeekNumber = weekNumber + 1;
    startPeriod = QTimeUtils.getFirstDayOfWeekInYear(year, nextWeekNumber);
  }
}

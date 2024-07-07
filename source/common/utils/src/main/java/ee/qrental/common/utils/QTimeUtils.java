package ee.qrental.common.utils;

import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.LocalDate.of;
import static java.time.Month.*;
import static java.time.temporal.IsoFields.WEEK_OF_WEEK_BASED_YEAR;
import static java.time.temporal.TemporalAdjusters.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.Locale;
import lombok.experimental.UtilityClass;

@UtilityClass
public class QTimeUtils {

  public static final DateTimeFormatter Q_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

  public static final LocalDate INVOICE_START_CALCULATION_DATE = LocalDate.of(2023, MARCH, 05);
  public static final LocalDate BALANCE_START_CALCULATION_DATE = LocalDate.of(2023, JANUARY, 02);
  public static final LocalDate RENT_START_CALCULATION_DATE = LocalDate.of(2023, JANUARY, 01);

  public static int getWeekNumber(final LocalDate date) {
    return date.get(WeekFields.of(Locale.UK).weekOfWeekBasedYear());
  }

  public static LocalDate getFirstDayOfYear(final Integer year) {
    return of(year, 1, 1).with(firstDayOfYear());
  }

  public static LocalDate getLastDayOfYear(final Integer year) {
    return of(year, 1, 1).with(lastDayOfYear());
  }

  public static LocalDate getFirstDayOfWeekInYear(final Integer year, final Integer weekNumber) {
    return getDayByYearAndWeekNumberAndDayOfWeek(year, weekNumber, MONDAY);
  }

  public static LocalDate getLastDayOfWeekInYear(final Integer year, final Integer weekNumber) {
    return getDayByYearAndWeekNumberAndDayOfWeek(year, weekNumber, SUNDAY);
  }

  public static LocalDate getLastSundayFromDate(final LocalDate fromDate) {
    if (fromDate == null) {
      throw new RuntimeException("Impossible to calculate last Sunday, if fromDate is NULL.");
    }
    if (fromDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
      return fromDate;
    }
    final var currentYear = fromDate.getYear();
    final var currentWeekNumber = getWeekNumber(fromDate);
    final var actionWeekNumber = currentWeekNumber - 1;

    return getLastDayOfWeekInYear(currentYear, actionWeekNumber);
  }

  private static LocalDate getDayByYearAndWeekNumberAndDayOfWeek(
      final Integer year, final Integer weekNumber, final DayOfWeek dayOfWeek) {
    return LocalDate.of(year, JUNE, 1)
        .with(previousOrSame(dayOfWeek))
        .with(WEEK_OF_WEEK_BASED_YEAR, weekNumber);
  }
}

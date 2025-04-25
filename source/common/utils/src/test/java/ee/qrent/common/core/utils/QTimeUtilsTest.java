package ee.qrent.common.core.utils;


import org.junit.jupiter.api.Test;

import static ee.qrent.common.utils.QTimeUtils.getLastSundayFromDate;
import static ee.qrent.common.utils.QTimeUtils.getWeekNumber;
import static org.junit.jupiter.api.Assertions.assertEquals;


import java.time.LocalDate;
import java.time.Month;


class QTimeUtilsTest {

  @Test
  public void testGetLastSundayFromDateIfFromDateIsNull() {
    // given
    final LocalDate fromDate = null;

    // when
    Exception exception = null;
    try {
      getLastSundayFromDate(fromDate);
    } catch (Exception e) {
      exception = e;
    }

    // then
    assertEquals(RuntimeException.class, exception.getClass());
    assertEquals(
        "Impossible to calculate last Sunday, if fromDate is NULL.", exception.getMessage());
  }

  @Test
  public void testGetLastSundayFromDateIfFromDateIsSunday() {
    // given
    final var fromDateSunday = LocalDate.of(2023, Month.MARCH, 5);

    // when
    final var result = getLastSundayFromDate(fromDateSunday);

    // then
    assertEquals(fromDateSunday, result);
  }

  @Test
  public void testGetLastSundayFromDateIfFromDateIsSaturday() {
    // given
    final var fromDateSaturday = LocalDate.of(2023, Month.MARCH, 4);

    // when
    final var result = getLastSundayFromDate(fromDateSaturday);

    // then
    assertEquals(LocalDate.of(2023, Month.FEBRUARY, 26), result);
  }

  @Test
  public void testGetLastSundayFromDateIfFromDateIsMonday() {
    // given
    final var fromDateMonday = LocalDate.of(2023, Month.MARCH, 6);

    // when
    final var result = getLastSundayFromDate(fromDateMonday);

    // then
    assertEquals(LocalDate.of(2023, Month.MARCH, 5), result);
  }

  @Test
  public void testGetWeekNumberForSecondJanuary() {
    // given
    final var secondJanuary2023 = LocalDate.of(2023, Month.JANUARY, 2);

    // when
    final var result = getWeekNumber(secondJanuary2023);

    // then
    assertEquals(1, result);
  }
}

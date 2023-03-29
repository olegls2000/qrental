package ee.qrental.common.core.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static ee.qrental.common.core.utils.QTimeUtils.getLastSundayFromDate;
import static org.junit.jupiter.api.Assertions.assertEquals;

class QTimeUtilsTest {

    @Test
    public void testGetLastSundayFromDateIfFromDateIsNull() {
        //given
        final LocalDate fromDate = null;

        //when
        Exception exception = null;
        try {
            getLastSundayFromDate(fromDate);
        } catch (Exception e) {
            exception = e;
        }

        //then
        assertEquals(RuntimeException.class, exception.getClass());
        assertEquals(
                "Impossible to calculate last Sunday, if fromDate is NULL.",
                exception.getMessage());
    }

    @Test
    public void testGetLastSundayFromDateIfFromDateIsSunday() {
        //given
        final var fromDateSunday = LocalDate.of(2023, Month.MARCH, 5);

        //when
        final var result = getLastSundayFromDate(fromDateSunday);

        //then
        assertEquals(fromDateSunday, result);
    }

    @Test
    public void testGetLastSundayFromDateIfFromDateIsSaturday() {
        //given
        final var fromDateSaturday = LocalDate.of(2023, Month.MARCH, 4);

        //when
        final var result = getLastSundayFromDate(fromDateSaturday);

        //then
        assertEquals(LocalDate.of(2023, Month.FEBRUARY, 26), result);
      }

    @Test
    public void testGetLastSundayFromDateIfFromDateIsMonday() {
        //given
        final var fromDateMonday = LocalDate.of(2023, Month.MARCH, 6);

        //when
        final var result = getLastSundayFromDate(fromDateMonday);

        //then
        assertEquals(LocalDate.of(2023, Month.MARCH, 5), result);
    }
}
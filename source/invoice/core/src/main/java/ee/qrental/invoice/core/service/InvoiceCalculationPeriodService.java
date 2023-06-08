package ee.qrental.invoice.core.service;

import static ee.qrental.common.core.utils.QTimeUtils.getLastDayOfWeekInYear;
import static java.time.temporal.ChronoUnit.DAYS;

import ee.qrental.common.core.utils.QWeek;
import ee.qrental.common.core.utils.QWeekIterator;
import ee.qrental.invoice.api.out.InvoiceCalculationLoadPort;
import java.time.LocalDate;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvoiceCalculationPeriodService {

  private final InvoiceCalculationLoadPort loadPort;

  public QWeekIterator getWeekIterator(final Integer lastYear, final QWeek lastWeek) {
    final var startDate = loadPort.loadLastCalculationDate().plus(1, DAYS);
    final var endDate = getEndCalculationDate(lastYear, lastWeek);

    return new QWeekIterator(startDate, endDate);
  }

  private LocalDate getEndCalculationDate(final Integer year, final QWeek lastWeek) {
    return getLastDayOfWeekInYear(year, lastWeek.getNumber());
  }
}

package ee.qrental.invoice.core.service;

import static ee.qrental.common.core.utils.QTimeUtils.getLastSundayFromDate;

import ee.qrental.common.core.utils.QWeekIterator;
import ee.qrental.invoice.api.out.InvoiceCalculationLoadPort;
import java.time.LocalDate;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvoiceCalculationPeriodService {

  private final InvoiceCalculationLoadPort loadPort;

  public QWeekIterator getWeekIterator(final LocalDate actionDate) {
    final var startDate = loadPort.loadLastCalculationDate();
    final var endDate = getEndCalculationDate(actionDate);

    return new QWeekIterator(startDate, endDate);
  }

  private LocalDate getEndCalculationDate(final LocalDate actionDate) {
    return getLastSundayFromDate(actionDate);
  }
}

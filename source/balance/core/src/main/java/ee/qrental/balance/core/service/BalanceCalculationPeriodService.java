package ee.qrental.balance.core.service;

import static ee.qrental.common.core.utils.QTimeUtils.getLastSundayFromDate;

import ee.qrental.balance.api.out.BalanceCalculationLoadPort;
import ee.qrental.common.core.utils.QWeekIterator;
import java.time.LocalDate;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BalanceCalculationPeriodService {

  private final BalanceCalculationLoadPort loadPort;

  public QWeekIterator getWeekIterator(final LocalDate actionDate) {
    final var startDate = loadPort.loadLastCalculationDate();
    final var endDate = getEndCalculationDate(actionDate);

    return new QWeekIterator(startDate, endDate);
  }

  private LocalDate getEndCalculationDate(final LocalDate actionDate) {
    return getLastSundayFromDate(actionDate);
  }
}

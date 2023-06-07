package ee.qrental.balance.core.service;

import static ee.qrental.common.core.utils.QTimeUtils.getFirstDayOfWeekInYear;
import static ee.qrental.common.core.utils.QTimeUtils.getLastDayOfWeekInYear;

import ee.qrental.balance.api.out.BalanceCalculationLoadPort;
import ee.qrental.common.core.utils.QWeek;
import ee.qrental.common.core.utils.QWeekIterator;
import java.time.LocalDate;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BalanceCalculationPeriodService {

  private final BalanceCalculationLoadPort loadPort;

  public QWeekIterator getWeekIterator(final Integer lastYear, final QWeek lastWeek) {
    final var startDate = loadPort.loadLastCalculationDate();
    final var endDate = getEndCalculationDate(lastYear, lastWeek);

    return new QWeekIterator(startDate, endDate);
  }

  private LocalDate getEndCalculationDate(final Integer year, final QWeek lastWeek) {
    return getLastDayOfWeekInYear(year, lastWeek.getNumber());
  }
}

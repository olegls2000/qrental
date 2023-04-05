package ee.qrental.transaction.core.service.strategy;

import ee.qrental.common.core.utils.QTimeUtils;
import ee.qrental.common.core.utils.QWeek;
import ee.qrental.transaction.api.in.query.filter.YearAndWeekAndDriverFilter;
import ee.qrental.transaction.api.out.TransactionLoadPort;
import ee.qrental.transaction.domain.Transaction;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TransactionLoadStrategyByDriverAndYearAndWeek implements TransactionLoadStrategy {

  private final TransactionLoadPort transactionLoadPort;

  @Override
  public boolean canApply(final YearAndWeekAndDriverFilter request) {
    return request.getDriverId() != null && request.getWeek() != QWeek.ALL;
  }

  @Override
  public List<Transaction> load(YearAndWeekAndDriverFilter request) {
    final var year = request.getYear();
    final var weekNumber = request.getWeek().getNumber();

    return transactionLoadPort.loadAllByDriverIdAndBetweenDays(
        request.getDriverId(),
        QTimeUtils.getFirstDayOfWeekInYear(year, weekNumber),
        QTimeUtils.getLastDayOfWeekInYear(year, weekNumber));
  }
}

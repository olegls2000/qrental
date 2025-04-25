package ee.qrent.billing.transaction.core.service.strategy;


import ee.qrent.common.utils.QWeek;
import ee.qrent.billing.transaction.api.in.query.filter.YearAndWeekAndDriverAndFeeFilter;
import ee.qrent.billing.transaction.api.out.TransactionLoadPort;
import ee.qrent.billing.transaction.domain.Transaction;
import java.util.List;
import lombok.AllArgsConstructor;

import static ee.qrent.common.utils.QTimeUtils.getFirstDayOfYear;
import static ee.qrent.common.utils.QTimeUtils.getLastDayOfYear;

@AllArgsConstructor
public class TransactionLoadStrategyByDriverAndYear implements TransactionLoadStrategy {

  private final TransactionLoadPort transactionLoadPort;

  @Override
  public boolean canApply(final YearAndWeekAndDriverAndFeeFilter request) {
    return request.getDriverId() != null && request.getWeek() == QWeek.ALL;
  }

  @Override
  public List<Transaction> load(final YearAndWeekAndDriverAndFeeFilter request) {
    final var year = request.getYear();

    return transactionLoadPort.loadAllByDriverIdAndBetweenDays(
        request.getDriverId(),
        getFirstDayOfYear(year),
        getLastDayOfYear(year));
  }
}

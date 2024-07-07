package ee.qrental.transaction.core.service.strategy;

import ee.qrental.common.utils.QTimeUtils;
import ee.qrental.common.utils.QWeek;
import ee.qrental.transaction.api.in.query.filter.YearAndWeekAndDriverAndFeeFilter;
import ee.qrental.transaction.api.out.TransactionLoadPort;
import ee.qrental.transaction.domain.Transaction;
import java.util.List;
import lombok.AllArgsConstructor;

import static ee.qrental.common.utils.QTimeUtils.getFirstDayOfYear;

@AllArgsConstructor
public class TransactionLoadStrategyByYear implements TransactionLoadStrategy {

  private final TransactionLoadPort transactionLoadPort;

  @Override
  public boolean canApply(final YearAndWeekAndDriverAndFeeFilter request) {
    return request.getDriverId() == null && request.getWeek() == QWeek.ALL;
  }

  @Override
  public List<Transaction> load(final YearAndWeekAndDriverAndFeeFilter request) {
    final var year = request.getYear();

    return transactionLoadPort.loadAllBetweenDays(
        getFirstDayOfYear(year), QTimeUtils.getLastDayOfYear(year));
  }
}

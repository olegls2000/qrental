package ee.qrental.transaction.core.service.strategy;


import ee.qrental.common.utils.QWeek;
import ee.qrental.transaction.api.in.query.filter.YearAndWeekAndDriverAndFeeFilter;
import ee.qrental.transaction.api.out.TransactionLoadPort;
import ee.qrental.transaction.domain.Transaction;
import java.util.List;
import lombok.AllArgsConstructor;

import static ee.qrental.common.utils.QTimeUtils.getFirstDayOfWeekInYear;
import static ee.qrental.common.utils.QTimeUtils.getLastDayOfWeekInYear;

@AllArgsConstructor
public class TransactionLoadStrategyByDriverAndYearAndWeekAndFee
    implements TransactionLoadStrategy {

  private final TransactionLoadPort transactionLoadPort;

  @Override
  public boolean canApply(final YearAndWeekAndDriverAndFeeFilter request) {
    return request.getDriverId() != null && request.getWeek() != QWeek.ALL;
  }

  @Override
  public List<Transaction> load(final YearAndWeekAndDriverAndFeeFilter request) {
    final var year = request.getYear();
    final var weekNumber = request.getWeek().getNumber();
    final var driverId = request.getDriverId();
    final var startDate = getFirstDayOfWeekInYear(year, weekNumber);
    final var endDate = getLastDayOfWeekInYear(year, weekNumber);
      return switch (request.getFeeOption()) {
          case WITH_FEE -> transactionLoadPort.loadAllByDriverIdAndBetweenDays(
                  driverId, startDate, endDate);
          case WITHOUT_FEE -> transactionLoadPort.loadAllNonFeeByDriverIdAndBetweenDays(
                  request.getDriverId(), startDate, endDate);
          case ONLY_FEE -> transactionLoadPort.loadAllFeeByDriverIdAndBetweenDays(
                  request.getDriverId(), startDate, endDate);
      };
  }
}

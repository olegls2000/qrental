package ee.qrental.transaction.core.validator;

import static java.lang.String.format;
import static java.time.format.DateTimeFormatter.ofLocalizedDate;
import static java.time.format.FormatStyle.MEDIUM;

import ee.qrent.common.in.validation.ViolationsCollector;
import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.transaction.api.in.request.TransactionAddRequest;
import ee.qrental.transaction.api.out.balance.BalanceLoadPort;
import ee.qrental.transaction.domain.balance.Balance;
import java.time.LocalDate;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TransactionAddBusinessRuleValidator {

  private final GetQWeekQuery qWeekQuery;
  private final BalanceLoadPort balanceLoadPort;

  public ViolationsCollector validateAdd(final TransactionAddRequest request) {
    final var violationsCollector = new ViolationsCollector();
    checkDTransactionTypeForAdd(request, violationsCollector);

    final var latestBalance = balanceLoadPort.loadLatestByDriverId(request.getDriverId());
    if (latestBalance == null) {
      return violationsCollector;
    }
    final var latestBalanceDate = getLatestBalanceDate(latestBalance);
    checkDateForAdd(latestBalanceDate, request, violationsCollector);

    return violationsCollector;
  }

  private void checkDTransactionTypeForAdd(
      final TransactionAddRequest request, final ViolationsCollector violationCollector) {
    if (request.getTransactionTypeId() == null) {
      violationCollector.collect("Transaction Type mus be selected");
    }
  }

  private void checkDateForAdd(
      final LocalDate balanceLatestCalculatedDate,
      final TransactionAddRequest request,
      final ViolationsCollector violationCollector) {
    final var transactionDate = request.getDate();

    if (transactionDate.isBefore(balanceLatestCalculatedDate)
        || transactionDate.equals(balanceLatestCalculatedDate)) {
      final var formattedTransactionDate = transactionDate.format(ofLocalizedDate(MEDIUM));
      final var formattedBalanceCalculatedDate =
          balanceLatestCalculatedDate.format(ofLocalizedDate(MEDIUM));
      violationCollector.collect(
          format(
              "Transaction date %s must be after the latest calculated Balance date: %s",
              formattedTransactionDate, formattedBalanceCalculatedDate));
    }
  }

  private LocalDate getLatestBalanceDate(final Balance balance) {
    final var latestBalanceQWeek = qWeekQuery.getById(balance.getQWeekId());
    return latestBalanceQWeek.getEnd();
  }
}

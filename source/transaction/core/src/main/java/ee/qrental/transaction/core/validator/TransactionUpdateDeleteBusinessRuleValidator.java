package ee.qrental.transaction.core.validator;

import static java.lang.String.format;
import static java.time.format.DateTimeFormatter.ofLocalizedDate;
import static java.time.format.FormatStyle.MEDIUM;

import ee.qrental.common.core.in.validation.QValidator;
import ee.qrental.common.core.in.validation.ViolationsCollector;
import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.transaction.api.out.TransactionLoadPort;
import ee.qrental.transaction.api.out.balance.BalanceLoadPort;
import ee.qrental.transaction.domain.Transaction;
import ee.qrental.transaction.domain.balance.Balance;
import java.time.LocalDate;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TransactionUpdateDeleteBusinessRuleValidator implements QValidator<Transaction> {

  private final GetQWeekQuery qWeekQuery;
  private final TransactionLoadPort transactionLoadPort;
  private final BalanceLoadPort balanceLoadPort;

  @Override
  public ViolationsCollector validateAdd(final Transaction domain) {
    final var driverId = domain.getDriverId();
    final var violationsCollector = new ViolationsCollector();

    final var latestBalance = balanceLoadPort.loadLatestByDriverId(driverId);
    if (latestBalance == null) {
      return violationsCollector;
    }
    final var latestBalanceDate = getLatestBalanceDate(latestBalance);
    checkDateForAdd(latestBalanceDate, domain, violationsCollector);

    return violationsCollector;
  }

  @Override
  public ViolationsCollector validateUpdate(final Transaction domain) {
    final var violationsCollector = new ViolationsCollector();
    final var transactionId = domain.getId();
    final var transactionFromDb = transactionLoadPort.loadById(transactionId);
    final var latestBalance = balanceLoadPort.loadLatest();
    if (latestBalance == null) {
      return violationsCollector;
    }

    final var latestBalanceCalculatedDate = getLatestBalanceDate(latestBalance);

    checkExistence(domain.getId(), transactionFromDb, violationsCollector);
    checkIfUpdateAllowed(latestBalanceCalculatedDate, transactionFromDb, violationsCollector);
    checkNewDate(latestBalanceCalculatedDate, domain, violationsCollector);

    return violationsCollector;
  }

  @Override
  public ViolationsCollector validateDelete(final Long id) {
    final var violationsCollector = new ViolationsCollector();
    final var latestBalance = balanceLoadPort.loadLatest();
    if (latestBalance == null) {

      return violationsCollector;
    }
    final var latestBalanceQWeek = qWeekQuery.getById(latestBalance.getQWeekId());
    final var latestBalanceCalculatedDate = latestBalanceQWeek.getEnd();

    final var transactionFromDb = transactionLoadPort.loadById(id);
    checkIfDeleteAllowed(latestBalanceCalculatedDate, transactionFromDb, violationsCollector);

    return violationsCollector;
  }

  private void checkDTransactionTypeForAdd(
      final Transaction domain, final ViolationsCollector violationCollector) {
    if (domain.getType() == null) {
      violationCollector.collect("Transaction Type mus be selected");
    }
  }

  private void checkDateForAdd(
      final LocalDate balanceLatestCalculatedDate,
      final Transaction domain,
      final ViolationsCollector violationCollector) {
    final var transactionDate = domain.getDate();

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

  private void checkIfUpdateAllowed(
      final LocalDate balanceLatestCalculatedDate,
      final Transaction fromDb,
      final ViolationsCollector violationCollector) {
    final var transactionDate = fromDb.getDate();
    final var transactionId = fromDb.getId();
    if (transactionDate.isBefore(balanceLatestCalculatedDate)
        || transactionDate.equals(balanceLatestCalculatedDate)) {
      violationCollector.collect(
          format(
              "Update for the Transaction with id=%d is prohibited. Transaction is already calculated in Balance",
              transactionId));
    }
  }

  private void checkNewDate(
      final LocalDate balanceLatestCalculatedDate,
      final Transaction domain,
      final ViolationsCollector violationCollector) {
    final var transactionDate = domain.getDate();
    if (transactionDate.isBefore(balanceLatestCalculatedDate)
        || transactionDate.equals(balanceLatestCalculatedDate)) {
      final var formattedTransactionDate = transactionDate.format(ofLocalizedDate(MEDIUM));
      final var formattedBalanceCalculatedDate =
          balanceLatestCalculatedDate.format(ofLocalizedDate(MEDIUM));
      violationCollector.collect(
          format(
              "Transaction new date %s must be after the latest calculated Balance date: %s",
              formattedTransactionDate, formattedBalanceCalculatedDate));
    }
  }

  private void checkIfDeleteAllowed(
      final LocalDate balanceLatestCalculatedDate,
      final Transaction fromDb,
      final ViolationsCollector violationCollector) {
    final var transactionDate = fromDb.getDate();
    final var transactionId = fromDb.getId();
    if (transactionDate.isBefore(balanceLatestCalculatedDate)
        || transactionDate.equals(balanceLatestCalculatedDate)) {
      violationCollector.collect(
          format(
              "Delete for the Transaction with id=%d is prohibited. Transaction is already calculated in Balance",
              transactionId));
    }
  }

  private void checkExistence(
      final Long id, final Transaction fromDb, final ViolationsCollector violationCollector) {
    if (fromDb == null) {
      violationCollector.collect("Update of Transaction failed. No Record with id = " + id);
    }
  }

  private LocalDate getLatestBalanceDate(final Balance balance) {
    final var latestBalanceQWeek = qWeekQuery.getById(balance.getQWeekId());
    return latestBalanceQWeek.getEnd();
  }
}

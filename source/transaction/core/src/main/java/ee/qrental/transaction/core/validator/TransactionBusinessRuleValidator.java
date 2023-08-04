package ee.qrental.transaction.core.validator;

import static java.lang.String.format;
import static java.time.format.DateTimeFormatter.ofLocalizedDate;
import static java.time.format.FormatStyle.MEDIUM;

import ee.qrental.common.core.in.validation.QValidator;
import ee.qrental.common.core.in.validation.ViolationsCollector;
import ee.qrental.transaction.api.out.TransactionLoadPort;
import ee.qrental.transaction.api.out.balance.BalanceLoadPort;
import ee.qrental.transaction.domain.Transaction;
import java.time.LocalDate;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TransactionBusinessRuleValidator implements QValidator<Transaction> {

  private final TransactionLoadPort transactionLoadPort;
  private final BalanceLoadPort balanceLoadPort;

  @Override
  public ViolationsCollector validateAdd(final Transaction domain) {
    final var driverId = domain.getDriverId();
    final var latestBalanceCalculatedDate = balanceLoadPort.loadLatestCalculatedDateOrDefaultByDriverId(driverId);
    final var violationsCollector = new ViolationsCollector();
    checkDateForAdd(latestBalanceCalculatedDate, domain, violationsCollector);

    return violationsCollector;
  }

  @Override
  public ViolationsCollector validateUpdate(final Transaction domain) {
    final var violationsCollector = new ViolationsCollector();
    final var transactionId = domain.getId();
    final var transactionFromDb = transactionLoadPort.loadById(transactionId);
    final var latestBalanceCalculatedDate = balanceLoadPort.loadLatestCalculatedDateOrDefault();

    checkExistence(domain.getId(), transactionFromDb, violationsCollector);
    checkIfUpdateAllowed(latestBalanceCalculatedDate, transactionFromDb, violationsCollector);
    checkNewDate(latestBalanceCalculatedDate, domain, violationsCollector);

    return violationsCollector;
  }

  @Override
  public ViolationsCollector validateDelete(final Long id) {
    final var violationsCollector = new ViolationsCollector();
    final var latestBalanceCalculatedDate = balanceLoadPort.loadLatestCalculatedDateOrDefault();
    final var transactionFromDb = transactionLoadPort.loadById(id);
    checkIfDeleteAllowed(latestBalanceCalculatedDate, transactionFromDb, violationsCollector);

    return violationsCollector;
  }

  private void checkDateForAdd(
      final LocalDate balanceLatestCalculatedDate,
      final Transaction domain,
      final ViolationsCollector violationCollector) {
    final var transactionDate = domain.getDate();

    if (transactionDate.isBefore(balanceLatestCalculatedDate)
        || transactionDate.equals(balanceLatestCalculatedDate)) {
      final var formattedTransactionDate = transactionDate.format(ofLocalizedDate(MEDIUM));
      final var formattedBalanceCalculatedDate = balanceLatestCalculatedDate.format(ofLocalizedDate(MEDIUM));
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
      final var formattedBalanceCalculatedDate = balanceLatestCalculatedDate.format(ofLocalizedDate(MEDIUM));
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
}

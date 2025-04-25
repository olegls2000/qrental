package ee.qrent.billing.transaction.core.validator;

import ee.qrent.common.in.validation.ViolationsCollector;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.transaction.api.out.TransactionLoadPort;
import ee.qrent.billing.transaction.api.out.balance.BalanceLoadPort;
import ee.qrent.billing.transaction.domain.balance.Balance;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

import static java.lang.String.format;
import static java.time.format.DateTimeFormatter.ofLocalizedDate;
import static java.time.format.FormatStyle.MEDIUM;

@AllArgsConstructor
public abstract class AbstractTransactionRequestValidator {

  private final GetQWeekQuery qWeekQuery;
  private final BalanceLoadPort balanceLoadPort;

  @Getter(AccessLevel.PROTECTED)
  private final TransactionLoadPort transactionLoadPort;

  protected void checkTransactionType(
      final Long transactionTypeId, final ViolationsCollector violationCollector) {
    if (transactionTypeId == null) {
      violationCollector.collect("Transaction Type must be selected");
    }
  }

  protected void checkAmount(
      final BigDecimal amount, final ViolationsCollector violationsCollector) {
    if (amount == null) {
      violationsCollector.collect("Amount must be present");
      return;
    }
    if (amount.compareTo(BigDecimal.ZERO) <= 0) {
      violationsCollector.collect("Amount must be Positive and greater than zero");
    }
  }

  protected void checkDate(
      final LocalDate transactionDate,
      final Long driverId,
      final ViolationsCollector violationsCollector) {
    final var latestBalance = balanceLoadPort.loadLatestByDriverId(driverId);
    if (latestBalance == null) {
      return;
    }
    final var balanceLatestCalculatedDate = getLatestBalanceDate(latestBalance);
    if (transactionDate.isBefore(balanceLatestCalculatedDate)
        || transactionDate.equals(balanceLatestCalculatedDate)) {
      final var formattedTransactionDate = transactionDate.format(ofLocalizedDate(MEDIUM));
      final var formattedBalanceCalculatedDate =
          balanceLatestCalculatedDate.format(ofLocalizedDate(MEDIUM));
      violationsCollector.collect(
          format(
              "Any operations with Transaction are prohibited because a Transaction's date (new or existing) %s is before or equals the latest calculated Balance date: %s",
              formattedTransactionDate, formattedBalanceCalculatedDate));
    }
  }

  private LocalDate getLatestBalanceDate(final Balance balance) {
    final var latestBalanceQWeek = qWeekQuery.getById(balance.getQWeekId());
    return latestBalanceQWeek.getEnd();
  }
}

package ee.qrent.billing.transaction.core.validator;

import ee.qrent.common.in.validation.AddRequestValidator;
import ee.qrent.common.in.validation.ViolationsCollector;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.transaction.api.in.request.TransactionAddRequest;
import ee.qrent.billing.transaction.api.out.TransactionLoadPort;
import ee.qrent.billing.transaction.api.out.balance.BalanceLoadPort;

public class TransactionAddRequestValidator extends AbstractTransactionRequestValidator
    implements AddRequestValidator<TransactionAddRequest> {

  public TransactionAddRequestValidator(
      GetQWeekQuery qWeekQuery,
      BalanceLoadPort balanceLoadPort,
      TransactionLoadPort transactionLoadPort) {
    super(qWeekQuery, balanceLoadPort, transactionLoadPort);
  }

  public ViolationsCollector validate(final TransactionAddRequest request) {
    final var violationsCollector = new ViolationsCollector();
    checkTransactionType(request.getTransactionTypeId(), violationsCollector);
    checkAmount(request.getAmount(), violationsCollector);
    checkDate(request.getDate(), request.getDriverId(), violationsCollector);

    return violationsCollector;
  }
}

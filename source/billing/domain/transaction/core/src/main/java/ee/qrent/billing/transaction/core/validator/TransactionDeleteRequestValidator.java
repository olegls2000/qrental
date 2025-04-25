package ee.qrent.billing.transaction.core.validator;

import ee.qrent.common.in.validation.DeleteRequestValidator;
import ee.qrent.common.in.validation.ViolationsCollector;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.transaction.api.in.request.TransactionDeleteRequest;
import ee.qrent.billing.transaction.api.out.TransactionLoadPort;
import ee.qrent.billing.transaction.api.out.balance.BalanceLoadPort;

public class TransactionDeleteRequestValidator extends AbstractTransactionRequestValidator
    implements DeleteRequestValidator<TransactionDeleteRequest> {

  public TransactionDeleteRequestValidator(
      final GetQWeekQuery qWeekQuery,
      final BalanceLoadPort balanceLoadPort,
      final TransactionLoadPort transactionLoadPort) {
    super(qWeekQuery, balanceLoadPort, transactionLoadPort);
  }

  @Override
  public ViolationsCollector validate(final TransactionDeleteRequest request) {
    final var violationsCollector = new ViolationsCollector();
    final var transactionFromDb = getTransactionLoadPort().loadById(request.getId());
    checkDate(transactionFromDb.getDate(), transactionFromDb.getDriverId(), violationsCollector);

    return violationsCollector;
  }
}

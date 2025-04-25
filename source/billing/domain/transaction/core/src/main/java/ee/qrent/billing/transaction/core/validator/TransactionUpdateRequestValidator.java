package ee.qrent.billing.transaction.core.validator;

import ee.qrent.common.in.validation.UpdateRequestValidator;
import ee.qrent.common.in.validation.ViolationsCollector;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.transaction.api.in.request.TransactionUpdateRequest;
import ee.qrent.billing.transaction.api.out.TransactionLoadPort;
import ee.qrent.billing.transaction.api.out.balance.BalanceLoadPort;

public class TransactionUpdateRequestValidator extends AbstractTransactionRequestValidator
    implements UpdateRequestValidator<TransactionUpdateRequest> {

  public TransactionUpdateRequestValidator(
      final GetQWeekQuery qWeekQuery,
      final BalanceLoadPort balanceLoadPort,
      final TransactionLoadPort transactionLoadPort) {
    super(qWeekQuery, balanceLoadPort, transactionLoadPort);
  }

  @Override
  public ViolationsCollector validate(final TransactionUpdateRequest request) {
    final var violationsCollector = new ViolationsCollector();
    final var transactionId = request.getId();
    final var driverId = request.getDriverId();
    final var transactionFromDb = getTransactionLoadPort().loadById(transactionId);
    checkAmount(request.getAmount(), violationsCollector);
    checkDate(transactionFromDb.getDate(), driverId, violationsCollector);
    checkDate(request.getDate(), driverId, violationsCollector);

    return violationsCollector;
  }
}

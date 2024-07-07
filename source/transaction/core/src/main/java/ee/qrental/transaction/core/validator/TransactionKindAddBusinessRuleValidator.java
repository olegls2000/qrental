package ee.qrental.transaction.core.validator;

import ee.qrent.common.in.validation.ViolationsCollector;
import ee.qrental.transaction.api.in.request.kind.TransactionKindAddRequest;
import ee.qrental.transaction.api.out.kind.TransactionKindLoadPort;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TransactionKindAddBusinessRuleValidator {

  private final TransactionKindLoadPort loadPort;

  public ViolationsCollector validate(final TransactionKindAddRequest addRequest) {
    final var violationsCollector = new ViolationsCollector();
    checkIfCodeIsUnique(addRequest, violationsCollector);

    return violationsCollector;
  }

  private void checkIfCodeIsUnique(
      final TransactionKindAddRequest addRequest, final ViolationsCollector violationCollector) {
    final var transactionKindFromDb = loadPort.loadByCode(addRequest.getCode());

    if (transactionKindFromDb != null) {
      violationCollector.collect("Transaction kind with code = %s already exists.");
    }
  }
}

package ee.qrental.transaction.core.validator;

import ee.qrent.common.in.validation.ViolationsCollector;
import ee.qrental.transaction.api.in.request.kind.TransactionKindUpdateRequest;
import ee.qrental.transaction.api.out.kind.TransactionKindLoadPort;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TransactionKindUpdateBusinessRuleValidator {

  private final TransactionKindLoadPort loadPort;

  public ViolationsCollector validate(final TransactionKindUpdateRequest updateRequest) {
    final var violationsCollector = new ViolationsCollector();
    checkExistence(updateRequest, violationsCollector);

    return violationsCollector;
  }

  private void checkExistence(
      final TransactionKindUpdateRequest updateRequest,
      final ViolationsCollector violationCollector) {
    final var id = updateRequest.getId();
    if (loadPort.loadById(id) == null) {
      violationCollector.collect("Update of Transaction Kind failed. No Record with id = " + id);
      throw new RuntimeException();
    }
  }
}

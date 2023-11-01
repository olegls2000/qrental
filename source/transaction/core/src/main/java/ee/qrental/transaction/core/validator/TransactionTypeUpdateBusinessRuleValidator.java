package ee.qrental.transaction.core.validator;

import ee.qrental.common.core.in.validation.ViolationsCollector;
import ee.qrental.transaction.api.in.request.type.TransactionTypeUpdateRequest;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TransactionTypeUpdateBusinessRuleValidator {

  public ViolationsCollector validate(final TransactionTypeUpdateRequest updateRequest) {
    final var violationsCollector = new ViolationsCollector();
    checkIfNegativeAndFeeAble(updateRequest, violationsCollector);

    return violationsCollector;
  }

  private void checkIfNegativeAndFeeAble(
      final TransactionTypeUpdateRequest updateRequest, final ViolationsCollector violationCollector) {
    if (!updateRequest.getNegative() && updateRequest.getFeeAble()) {
      violationCollector.collect("Fee able can be set only for Negative Transaction Types.");
    }
  }
}

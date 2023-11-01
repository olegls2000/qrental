package ee.qrental.transaction.core.validator;

import ee.qrental.common.core.in.validation.ViolationsCollector;
import ee.qrental.transaction.api.in.request.type.TransactionTypeAddRequest;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TransactionTypeAddBusinessRuleValidator {

  public ViolationsCollector validate(final TransactionTypeAddRequest addRequest) {
    final var violationsCollector = new ViolationsCollector();
    checkIfNegativeAndFeeAble(addRequest, violationsCollector);

    return violationsCollector;
  }

  private void checkIfNegativeAndFeeAble(
      final TransactionTypeAddRequest addRequest, final ViolationsCollector violationCollector) {
    if (!addRequest.getNegative() && addRequest.getFeeAble()) {
      violationCollector.collect("Fee able can be set only for Negative Transaction Types.");
    }
  }
}

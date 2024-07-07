package ee.qrental.transaction.core.validator;

import ee.qrent.common.in.validation.ViolationsCollector;
import ee.qrental.transaction.api.in.request.type.TransactionTypeUpdateRequest;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TransactionTypeUpdateBusinessRuleValidator {

  public ViolationsCollector validate(final TransactionTypeUpdateRequest updateRequest) {
    final var violationsCollector = new ViolationsCollector();

    return violationsCollector;
  }
}

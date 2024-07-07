package ee.qrental.transaction.core.validator;

import ee.qrent.common.in.validation.ViolationsCollector;
import ee.qrental.transaction.api.in.request.type.TransactionTypeAddRequest;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TransactionTypeAddBusinessRuleValidator {

  public ViolationsCollector validate(final TransactionTypeAddRequest addRequest) {
    final var violationsCollector = new ViolationsCollector();

    return violationsCollector;
  }
}

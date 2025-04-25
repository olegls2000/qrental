package ee.qrent.billing.transaction.core.validator;

import ee.qrent.common.in.validation.UpdateRequestValidator;
import ee.qrent.common.in.validation.ViolationsCollector;
import ee.qrent.billing.transaction.api.in.request.type.TransactionTypeUpdateRequest;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TransactionTypeUpdateRequestValidator implements UpdateRequestValidator<TransactionTypeUpdateRequest> {

  @Override
  public ViolationsCollector validate(final TransactionTypeUpdateRequest updateRequest) {
    final var violationsCollector = new ViolationsCollector();

    return violationsCollector;
  }
}

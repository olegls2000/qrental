package ee.qrent.billing.transaction.core.validator;

import ee.qrent.common.in.validation.AddRequestValidator;
import ee.qrent.common.in.validation.ViolationsCollector;
import ee.qrent.billing.transaction.api.in.request.type.TransactionTypeAddRequest;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TransactionTypeAddRequestValidator
    implements AddRequestValidator<TransactionTypeAddRequest> {

  @Override
  public ViolationsCollector validate(final TransactionTypeAddRequest addRequest) {
    final var violationsCollector = new ViolationsCollector();

    return violationsCollector;
  }
}

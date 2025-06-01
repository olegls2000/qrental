package ee.qrent.billing.deposit.core.validator;

import ee.qrent.billing.deposit.api.in.request.DepositAddRequest;
import ee.qrent.billing.deposit.api.in.request.DepositDeleteRequest;
import ee.qrent.billing.deposit.api.in.request.DepositUpdateRequest;
import ee.qrent.common.in.validation.AddRequestValidator;
import ee.qrent.common.in.validation.DeleteRequestValidator;
import ee.qrent.common.in.validation.UpdateRequestValidator;
import ee.qrent.common.in.validation.ViolationsCollector;

public class DepositRequestValidator
    implements AddRequestValidator<DepositAddRequest>,
        UpdateRequestValidator<DepositUpdateRequest>,
        DeleteRequestValidator<DepositDeleteRequest> {

  @Override
  public ViolationsCollector validate(final DepositAddRequest request) {

    return new ViolationsCollector() {};
  }

  @Override
  public ViolationsCollector validate(final DepositDeleteRequest request) {

    return new ViolationsCollector() {};
  }

  @Override
  public ViolationsCollector validate(final DepositUpdateRequest request) {

    return new ViolationsCollector() {};
  }
}

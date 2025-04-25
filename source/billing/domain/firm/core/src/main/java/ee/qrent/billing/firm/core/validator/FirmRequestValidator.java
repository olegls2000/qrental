package ee.qrent.billing.firm.core.validator;

import ee.qrent.common.in.validation.AddRequestValidator;
import ee.qrent.common.in.validation.DeleteRequestValidator;
import ee.qrent.common.in.validation.UpdateRequestValidator;
import ee.qrent.common.in.validation.ViolationsCollector;
import ee.qrent.billing.firm.api.in.request.FirmAddRequest;
import ee.qrent.billing.firm.api.in.request.FirmDeleteRequest;
import ee.qrent.billing.firm.api.in.request.FirmUpdateRequest;

public class FirmRequestValidator
    implements AddRequestValidator<FirmAddRequest>,
        UpdateRequestValidator<FirmUpdateRequest>,
        DeleteRequestValidator<FirmDeleteRequest> {

  @Override
  public ViolationsCollector validate(FirmAddRequest request) {
    return null;
  }

  @Override
  public ViolationsCollector validate(FirmDeleteRequest request) {
    return null;
  }

  @Override
  public ViolationsCollector validate(FirmUpdateRequest request) {
    return null;
  }
}

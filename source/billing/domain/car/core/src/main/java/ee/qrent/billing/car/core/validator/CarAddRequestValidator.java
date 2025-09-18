package ee.qrent.billing.car.core.validator;

import ee.qrent.billing.car.api.in.request.CarAddRequest;
import ee.qrent.billing.car.api.out.CarLoadPort;
import ee.qrent.common.in.validation.AddRequestValidator;
import ee.qrent.common.in.validation.AttributeChecker;
import ee.qrent.common.in.validation.ViolationsCollector;

public class CarAddRequestValidator extends AbstractCarRequestValidator
    implements AddRequestValidator<CarAddRequest> {

  public CarAddRequestValidator(
      final AttributeChecker attributeChecker,
      final CarLoadPort loadPort) {
    super(loadPort, attributeChecker);
  }

  @Override
  public ViolationsCollector validate(final CarAddRequest request) {
    final var violationsCollector = getViolationCollector();
    checkRegNumber(request.getRegNumber(), violationsCollector);
    checkVin(request.getVin(), violationsCollector);
    checkVinUniqueness(request.getVin(), violationsCollector);
    checkRegNumberUniqueness(request.getRegNumber(), violationsCollector);
    checkCustomRentAmount(
        request.getCustomRentActive(), request.getCustomRentAmount(), violationsCollector);

    return violationsCollector;
  }
}

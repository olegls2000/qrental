package ee.qrent.billing.car.core.validator;

import ee.qrent.billing.car.api.in.request.CarUpdateRequest;
import ee.qrent.billing.car.api.out.CarLoadPort;
import ee.qrent.common.in.validation.AttributeChecker;
import ee.qrent.common.in.validation.UpdateRequestValidator;
import ee.qrent.common.in.validation.ViolationsCollector;

public class CarUpdateRequestValidator extends AbstractCarRequestValidator
    implements UpdateRequestValidator<CarUpdateRequest> {

  public CarUpdateRequestValidator(
      final AttributeChecker attributeChecker, final CarLoadPort loadPort) {
    super(loadPort, attributeChecker);
  }

  @Override
  public ViolationsCollector validate(final CarUpdateRequest request) {
    final var violationsCollector = getViolationCollector();
    final var carFromDB = getLoadPort().loadById(request.getId());
    validateRegNumber(carFromDB.getRegNumber(), request.getRegNumber(), violationsCollector);
    validateCustomRentAmount(
        request.getCustomRentActive(), request.getCustomRentAmount(), violationsCollector);
    validateVin(carFromDB.getVin(), request.getVin(), violationsCollector);
    validateBoltIdentifier(carFromDB.getVin(), request.getVin(), violationsCollector);

    return violationsCollector;
  }

  private void validateBoltIdentifier(
      final String boltIdentifierFromDb,
      final String boltIdentifierFromRequest,
      ViolationsCollector violationsCollector) {
    if (boltIdentifierFromDb.equals(boltIdentifierFromRequest)) {

      return;
    }
    checkBoltIdentifierUniqueness(boltIdentifierFromRequest, violationsCollector);
  }

  private void validateRegNumber(
      final String regNumberFromDb,
      final String regNumberFromRequest,
      final ViolationsCollector violationsCollector) {
    checkRegNumber(regNumberFromRequest, violationsCollector);

    if (regNumberFromDb.equals(regNumberFromRequest)) {

      return;
    }
    checkRegNumberUniqueness(regNumberFromRequest, violationsCollector);
  }

  private void validateVin(
      final String vinFromDb,
      final String vinFromRequest,
      final ViolationsCollector violationsCollector) {
    checkVin(vinFromRequest, violationsCollector);
    if (vinFromDb.equals(vinFromRequest)) {

      return;
    }
    checkVinUniqueness(vinFromRequest, violationsCollector);
  }
}

package ee.qrental.car.core.validator;

import static java.lang.String.format;

import ee.qrental.car.api.in.request.CarLinkAddRequest;
import ee.qrental.car.api.out.CarLinkLoadPort;
import ee.qrent.common.in.validation.ViolationsCollector;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CarLinkAddBusinessRuleValidator {

  private final CarLinkLoadPort loadPort;

  public ViolationsCollector validate(final CarLinkAddRequest addRequest) {
    final var violationsCollector = new ViolationsCollector();
    checkIfCarAvailable(addRequest, violationsCollector);
    checkIfLinkStartDateAfterLatestRentCalculationDate(addRequest, violationsCollector);
    checkIfLinkStartDateAfterLatestLink(addRequest, violationsCollector);

    return violationsCollector;
  }

  private void checkIfLinkStartDateAfterLatestLink(
      final CarLinkAddRequest addRequest, final ViolationsCollector violationsCollector) {

  }

  private void checkIfLinkStartDateAfterLatestRentCalculationDate(
      final CarLinkAddRequest addRequest, final ViolationsCollector violationsCollector) {

  }

  private void checkIfCarAvailable(
      final CarLinkAddRequest addRequest, final ViolationsCollector violationCollector) {
    final var carId = addRequest.getCarId();
    final var activeLinks = loadPort.loadActiveByCarId(carId);
    if (activeLinks.isEmpty()) {

      return;
    }
    violationCollector.collect(format("Car with id: %d already linked.", carId));
  }
}

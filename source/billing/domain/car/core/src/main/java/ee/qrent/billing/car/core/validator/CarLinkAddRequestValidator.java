package ee.qrent.billing.car.core.validator;

import static java.lang.String.format;

import ee.qrent.common.in.validation.AddRequestValidator;
import ee.qrent.billing.car.api.in.request.CarLinkAddRequest;
import ee.qrent.billing.car.api.out.CarLinkLoadPort;
import ee.qrent.common.in.validation.ViolationsCollector;
import lombok.AllArgsConstructor;

//TODO: rename file

@AllArgsConstructor
public class CarLinkAddRequestValidator implements AddRequestValidator<CarLinkAddRequest> {

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

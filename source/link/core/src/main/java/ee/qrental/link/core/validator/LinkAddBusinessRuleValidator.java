package ee.qrental.link.core.validator;

import static java.lang.String.format;

import ee.qrental.common.core.in.validation.ViolationsCollector;
import ee.qrental.link.api.in.request.LinkAddRequest;
import ee.qrental.link.api.out.LinkLoadPort;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LinkAddBusinessRuleValidator {

  private final LinkLoadPort loadPort;

  public ViolationsCollector validate(final LinkAddRequest addRequest) {
    final var violationsCollector = new ViolationsCollector();
    checkIfCarAvailable(addRequest, violationsCollector);
    checkIfLinkStartDateAfterLatestBalanceCalculationDate(addRequest, violationsCollector);
    checkIfLinkStartDateAfterLatestLink(addRequest, violationsCollector);

    return violationsCollector;
  }

  private void checkIfLinkStartDateAfterLatestLink(
      final LinkAddRequest addRequest, final ViolationsCollector violationsCollector) {}

  private void checkIfLinkStartDateAfterLatestBalanceCalculationDate(
      final LinkAddRequest addRequest, final ViolationsCollector violationsCollector) {}

  private void checkIfCarAvailable(
      final LinkAddRequest addRequest, final ViolationsCollector violationCollector) {
    final var carId = addRequest.getCarId();
    final var activeLinks = loadPort.loadActiveByCarId(carId);
    if (activeLinks.isEmpty()) {

      return;
    }
    violationCollector.collect(format("Car with id: %d already linked.", carId));
  }
}

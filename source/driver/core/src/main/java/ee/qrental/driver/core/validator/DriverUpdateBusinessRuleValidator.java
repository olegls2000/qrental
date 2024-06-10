package ee.qrental.driver.core.validator;

import static java.lang.String.format;

import ee.qrental.common.core.in.validation.ViolationsCollector;
import ee.qrental.driver.api.in.request.DriverUpdateRequest;
import ee.qrental.driver.api.out.DriverLoadPort;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DriverUpdateBusinessRuleValidator {

  private final DriverLoadPort loadPort;

  public ViolationsCollector validate(final DriverUpdateRequest request) {
    final var violationsCollector = new ViolationsCollector();

    final var driverFromDb = loadPort.loadById(request.getId());
    final var friendshipFromDb = driverFromDb.getFriendship();
    if (friendshipFromDb == null) {
      return violationsCollector;
    }

    final var recommendedByFromDb = friendshipFromDb.getDriverId();
    final var recommendedByFromRequest = request.getRecommendedByDriverId();
    if (recommendedByFromDb.equals(recommendedByFromRequest)) {
      return violationsCollector;
    }

    if (friendshipFromDb.isUpdatable()) {
      return violationsCollector;
    }

    violationsCollector.collect(
        format("Driver's recommendation can not be changes after last creation week Sunday"));

    return violationsCollector;
  }
}

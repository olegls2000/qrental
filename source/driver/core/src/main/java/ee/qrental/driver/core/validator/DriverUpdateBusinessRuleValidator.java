package ee.qrental.driver.core.validator;

import static ee.qrental.common.core.utils.QTimeUtils.getLastDayOfWeekInYear;
import static ee.qrental.common.core.utils.QTimeUtils.getWeekNumber;
import static java.lang.String.format;

import ee.qrental.common.core.in.validation.ViolationsCollector;
import ee.qrental.driver.api.in.request.DriverUpdateRequest;
import ee.qrental.driver.api.out.DriverLoadPort;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

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

    if (isUpdatable(driverFromDb.getCreatedDate())) {
      return violationsCollector;
    }

    violationsCollector.collect(
        format("Driver's recommendation can not be changes after last creation week Sunday"));

    return violationsCollector;
  }

  public boolean isUpdatable(final LocalDate driverCreationDate) {
    final var weekNumber = getWeekNumber(driverCreationDate);
    final var year = driverCreationDate.getYear();
    final var lastDayAvailableForUpdate = getLastDayOfWeekInYear(year, weekNumber);
    return lastDayAvailableForUpdate.isAfter(LocalDate.now());
  }
}

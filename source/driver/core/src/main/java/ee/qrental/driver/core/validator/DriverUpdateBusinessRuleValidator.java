package ee.qrental.driver.core.validator;

import static ee.qrental.common.core.utils.QTimeUtils.getLastDayOfWeekInYear;
import static ee.qrental.common.core.utils.QTimeUtils.getWeekNumber;
import static java.lang.String.format;

import ee.qrental.common.core.in.validation.ViolationsCollector;
import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.driver.api.in.request.DriverUpdateRequest;
import ee.qrental.driver.api.out.DriverLoadPort;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@AllArgsConstructor
public class DriverUpdateBusinessRuleValidator {

  private final DriverLoadPort loadPort;
  private final GetQWeekQuery qWeekQuery;

  public ViolationsCollector validate(final DriverUpdateRequest request) {
    final var violationsCollector = new ViolationsCollector();
    // TODO: validation doesn't work for old drivers. Update is possible until week is not completed
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

    final var latestDateForUpdate = getLastDateForUpdate(driverFromDb.getCreatedDate());

    if (latestDateForUpdate.isAfter(LocalDate.now())) {
      return violationsCollector;
    }

    violationsCollector.collect(
        format(
            "Driver's recommendation can not be changes after creation week is passed. Last date for update was: "
                + latestDateForUpdate));

    return violationsCollector;
  }

  private LocalDate getLastDateForUpdate(final LocalDate driverCreationDate) {
    final var weekNumber = getWeekNumber(driverCreationDate);
    final var year = driverCreationDate.getYear();
    final var qWeek = qWeekQuery.getByYearAndNumber(year, weekNumber);

    return qWeek.getEnd();
  }
}

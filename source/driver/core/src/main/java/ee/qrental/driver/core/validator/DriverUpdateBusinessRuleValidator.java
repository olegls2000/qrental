package ee.qrental.driver.core.validator;

import static ee.qrental.common.utils.QTimeUtils.getWeekNumber;
import static java.lang.String.format;

import ee.qrent.common.in.time.QDateTime;
import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.driver.api.in.request.DriverUpdateRequest;
import ee.qrental.driver.api.out.DriverLoadPort;
import ee.qrental.driver.domain.Driver;
import ee.qrent.common.in.validation.ViolationsCollector;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.Objects;

@AllArgsConstructor
public class DriverUpdateBusinessRuleValidator {

  private final DriverLoadPort loadPort;
  private final GetQWeekQuery qWeekQuery;
  private final QDateTime qDateTime;

  public ViolationsCollector validate(final DriverUpdateRequest request) {
    final var violationsCollector = new ViolationsCollector();
    final var driverFromDb = loadPort.loadById(request.getId());
    final var isRecommendationUpdated = isRecommendationUpdated(request, driverFromDb);
    if (isRecommendationUpdated) {
      final var latestDateForUpdate = getLastDateForUpdate(driverFromDb.getCreatedDate());
      if (latestDateForUpdate.isBefore(qDateTime.getToday())) {
        violationsCollector.collect(
            format(
                "Driver's recommendation can not be changes after creation week is passed. Last date for update was: "
                    + latestDateForUpdate));
      }
    }

    return violationsCollector;
  }

  private LocalDate getLastDateForUpdate(final LocalDate driverCreationDate) {
    final var weekNumber = getWeekNumber(driverCreationDate);
    final var year = driverCreationDate.getYear();
    final var qWeek = qWeekQuery.getByYearAndNumber(year, weekNumber);

    return qWeek.getEnd();
  }

  private boolean isRecommendationUpdated(final DriverUpdateRequest request, final Driver fromDb) {
    final var recommendedByFromRequest = request.getRecommendedByDriverId();
    final var friendshipFromDb = fromDb.getFriendship();
    final var recommendedByFromDb =
        friendshipFromDb == null ? null : friendshipFromDb.getDriverId();

    return !Objects.equals(recommendedByFromRequest, recommendedByFromDb);
  }
}

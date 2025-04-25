package ee.qrent.billing.contract.core.mapper;

import static java.lang.String.format;

import ee.qrent.common.in.mapper.ResponseMapper;
import ee.qrent.billing.contract.api.in.response.AbsenceResponse;
import ee.qrent.billing.contract.domain.Absence;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AbsenceResponseMapper implements ResponseMapper<AbsenceResponse, Absence> {
  private final GetDriverQuery driverQuery;

  @Override
  public AbsenceResponse toResponse(final Absence domain) {
    if (domain == null) {
      return null;
    }
    final var driver = driverQuery.getById(domain.getDriverId());
    return AbsenceResponse.builder()
        .id(domain.getId())
        .dateStart(domain.getDateStart())
        .dateEnd(domain.getDateEnd())
        .driverId(domain.getDriverId())
        .driverFirstName(driver.getFirstName())
        .driverLastName(driver.getLastName())
        .driverIsikukood(driver.getIsikukood())
        .reason(domain.getReason().getDisplayValue())
        .withCar(domain.getWithCar())
        .comment(domain.getComment())
        .build();
  }

  @Override
  public String toObjectInfo(final Absence domain) {
    final var driver = driverQuery.getById(domain.getDriverId());
    final var startDateString = domain.getDateStart().toString();
    final var endDateString =
        domain.getDateEnd() == null ? "empty" : domain.getDateEnd().toString();

    return format(
        "Absence for Driver: %s %s and period: [%s ... %s]",
        driver.getFirstName(), driver.getLastName(), startDateString, endDateString);
  }
}

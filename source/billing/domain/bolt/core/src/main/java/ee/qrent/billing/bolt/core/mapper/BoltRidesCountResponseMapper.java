package ee.qrent.billing.bolt.core.mapper;

import static java.lang.String.format;

import ee.qrent.billing.bolt.api.in.response.BoltRidesCountResponse;
import ee.qrent.billing.bolt.domain.BoltOrdersCount;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.common.in.mapper.ResponseMapper;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BoltRidesCountResponseMapper
    implements ResponseMapper<BoltRidesCountResponse, BoltOrdersCount> {

  private final GetDriverQuery driverQuery;

  @Override
  public BoltRidesCountResponse toResponse(final BoltOrdersCount domain) {

    return BoltRidesCountResponse.builder()
        .id(domain.getId())
        .monthRidesCount(domain.getMonthOrdersCount())
        .driverName(getDriverName(domain))
        .build();
  }

  @Override
  public String toObjectInfo(final BoltOrdersCount domain) {

    return format(
        "Year: %d, month: %d Driver: %s",
        domain.getYear(), domain.getMonth(), getDriverName(domain));
  }

  private String getDriverName(final BoltOrdersCount domain) {
    final var driver = driverQuery.getById(domain.getDriverId());

    return format("%s %s", driver.getFirstName(), driver.getLastName());
  }
}

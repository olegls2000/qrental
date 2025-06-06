package ee.qrent.billing.bolt.core.mapper;

import static java.lang.String.format;

import ee.qrent.billing.bolt.api.in.response.BoltOrdersCountResponse;
import ee.qrent.billing.bolt.domain.BoltOrdersCount;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.common.in.mapper.ResponseMapper;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BoltOrdersCountResponseMapper
    implements ResponseMapper<BoltOrdersCountResponse, BoltOrdersCount> {

  private final GetDriverQuery getDriverQuery;

  @Override
  public BoltOrdersCountResponse toResponse(final BoltOrdersCount domain) {

    return BoltOrdersCountResponse.builder()
        .id(domain.getId())
        .monthOrdersCount(domain.getMonthOrdersCount())
        .driverName(getDriverName(domain))
        .build();
  }

  @Override
  public String toObjectInfo(final BoltOrdersCount domain) {

    return format("Year: %d, month: %d Driver: %s", domain.getMonth(), getDriverName(domain));
  }

  private String getDriverName(final BoltOrdersCount domain) {
    final var driver = getDriverQuery.getById(domain.getDriverId());

    return format("%s %s", driver.getFirstName(), driver.getLastName());
  }
}

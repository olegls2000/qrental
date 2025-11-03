package ee.qrent.billing.bolt.core.service;

import ee.qrent.billing.bolt.api.in.query.GetBoltRidesCountQuery;
import ee.qrent.billing.bolt.api.in.response.BoltRidesCountResponse;
import ee.qrent.billing.bolt.api.out.BoltRidesCountLoadPort;
import ee.qrent.billing.bolt.core.mapper.BoltRidesCountResponseMapper;
import lombok.AllArgsConstructor;

import java.util.HashSet;
import java.util.List;

@AllArgsConstructor
public class BoltOrdersCountQueryService implements GetBoltRidesCountQuery {

  private final BoltRidesCountLoadPort loadPort;
  private final BoltRidesCountResponseMapper mapper;

  @Override
  public List<BoltRidesCountResponse> getAllByYearAndMonth(
      final Integer year, final Integer month) {
    final var groupSet = new HashSet<String>();
    return loadPort.loadAllByYearAndMonth(year, month).stream()
        .filter(
            count ->
                groupSet.add(
                    count.getBoltId() + count.getYear().toString() + count.getMonth().toString()))
        .map(mapper::toResponse)
        .toList();
  }

  @Override
  public Integer getRidesCountByDriverIdAndQWeekId(final Long driverId, final Long qWeekId) {
    final var counter = loadPort.loadByDriverIdAndQWeekId(driverId, qWeekId);
    if (counter == null) {
      return Integer.valueOf(0);
    }
    return counter.getMonthOrdersCount();
  }
}

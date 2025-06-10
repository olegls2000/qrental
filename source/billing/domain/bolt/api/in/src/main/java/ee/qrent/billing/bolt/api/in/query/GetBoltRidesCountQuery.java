package ee.qrent.billing.bolt.api.in.query;

import ee.qrent.billing.bolt.api.in.response.BoltRidesCountResponse;

import java.util.List;

public interface GetBoltRidesCountQuery {
  List<BoltRidesCountResponse> getAllByYearAndMonth(final Integer year, final Integer month);

  Integer getRidesCountByDriverIdAndQWeekId(final Long driverId, final Long qWeekId);
}

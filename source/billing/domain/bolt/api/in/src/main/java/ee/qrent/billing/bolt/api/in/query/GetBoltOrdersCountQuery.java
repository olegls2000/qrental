package ee.qrent.billing.bolt.api.in.query;

import ee.qrent.billing.bolt.api.in.response.BoltOrdersCountResponse;

import java.util.List;

public interface GetBoltOrdersCountQuery {
  List<BoltOrdersCountResponse> getAllByYearAndMonth(final Integer year, final Integer month);
}

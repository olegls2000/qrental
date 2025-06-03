package ee.qrent.billing.bolt.api.in.query;

import ee.qrent.billing.bolt.api.in.request.BoltStatisticsUpdateRequest;
import ee.qrent.billing.bolt.api.in.response.BoltStatisticsResponse;
import ee.qrent.common.in.query.BaseGetQuery;


public interface GetBoltStatisticsQuery
    extends BaseGetQuery<BoltStatisticsUpdateRequest, BoltStatisticsResponse> {}

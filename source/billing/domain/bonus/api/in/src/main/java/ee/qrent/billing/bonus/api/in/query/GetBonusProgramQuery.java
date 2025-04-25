package ee.qrent.billing.bonus.api.in.query;

import ee.qrent.common.in.query.BaseGetQuery;
import ee.qrent.billing.bonus.api.in.request.BonusProgramUpdateRequest;
import ee.qrent.billing.bonus.api.in.response.BonusProgramResponse;

public interface GetBonusProgramQuery
    extends BaseGetQuery<BonusProgramUpdateRequest, BonusProgramResponse> {}

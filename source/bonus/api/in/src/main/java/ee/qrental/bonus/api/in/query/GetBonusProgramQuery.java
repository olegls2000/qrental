package ee.qrental.bonus.api.in.query;

import ee.qrent.common.in.query.BaseGetQuery;
import ee.qrental.bonus.api.in.request.BonusProgramUpdateRequest;
import ee.qrental.bonus.api.in.response.BonusProgramResponse;

public interface GetBonusProgramQuery
    extends BaseGetQuery<BonusProgramUpdateRequest, BonusProgramResponse> {}

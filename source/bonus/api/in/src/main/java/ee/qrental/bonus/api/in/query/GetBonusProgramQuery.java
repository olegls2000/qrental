package ee.qrental.bonus.api.in.query;

import ee.qrental.bonus.api.in.request.BonusProgramUpdateRequest;
import ee.qrental.bonus.api.in.response.BonusProgramResponse;
import ee.qrental.common.core.in.query.BaseGetQuery;

public interface GetBonusProgramQuery
    extends BaseGetQuery<BonusProgramUpdateRequest, BonusProgramResponse> {}

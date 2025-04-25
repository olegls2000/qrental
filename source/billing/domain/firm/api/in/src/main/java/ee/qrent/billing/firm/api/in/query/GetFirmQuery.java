package ee.qrent.billing.firm.api.in.query;

import ee.qrent.common.in.query.BaseGetQuery;
import ee.qrent.billing.firm.api.in.request.FirmUpdateRequest;
import ee.qrent.billing.firm.api.in.response.FirmResponse;

public interface GetFirmQuery extends BaseGetQuery<FirmUpdateRequest, FirmResponse> {}

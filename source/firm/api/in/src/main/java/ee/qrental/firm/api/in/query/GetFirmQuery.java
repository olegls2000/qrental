package ee.qrental.firm.api.in.query;

import ee.qrent.common.in.query.BaseGetQuery;
import ee.qrental.firm.api.in.request.FirmUpdateRequest;
import ee.qrental.firm.api.in.response.FirmResponse;

public interface GetFirmQuery extends BaseGetQuery<FirmUpdateRequest, FirmResponse> {}

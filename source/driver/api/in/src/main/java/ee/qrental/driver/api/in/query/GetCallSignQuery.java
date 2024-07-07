package ee.qrental.driver.api.in.query;

import ee.qrent.common.in.query.BaseGetQuery;
import ee.qrental.driver.api.in.request.CallSignUpdateRequest;
import ee.qrental.driver.api.in.response.CallSignResponse;

import java.util.List;

public interface GetCallSignQuery extends BaseGetQuery<CallSignUpdateRequest, CallSignResponse> {

  List<CallSignResponse> getAvailable();
}

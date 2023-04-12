package ee.qrental.constant.api.in.query;

import ee.qrental.common.core.in.query.BaseGetQuery;
import ee.qrental.constant.api.in.request.ConstantUpdateRequest;
import ee.qrental.constant.api.in.response.ConstantResponse;

public interface GetConstantQuery extends BaseGetQuery<ConstantUpdateRequest, ConstantResponse> {}

package ee.qrental.constant.api.in.query;

import ee.qrental.common.core.in.query.BaseGetQuery;
import ee.qrental.constant.api.in.request.ConstantUpdateRequest;
import ee.qrental.constant.api.in.response.constant.ConstantResponse;

public interface GetConstantQuery extends BaseGetQuery<ConstantUpdateRequest, ConstantResponse> {

    ConstantResponse getByName(final String name);

}


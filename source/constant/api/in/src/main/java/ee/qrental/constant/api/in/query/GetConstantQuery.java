package ee.qrental.constant.api.in.query;

import ee.qrent.common.in.query.BaseGetQuery;
import ee.qrental.constant.api.in.request.ConstantUpdateRequest;
import ee.qrental.constant.api.in.response.constant.ConstantResponse;

import java.math.BigDecimal;

public interface GetConstantQuery extends BaseGetQuery<ConstantUpdateRequest, ConstantResponse> {

    ConstantResponse getByName(final String name);

    BigDecimal getFeeWeeklyInterest();

}


package ee.qrent.billing.constant.api.in.query;

import ee.qrent.common.in.query.BaseGetQuery;
import ee.qrent.billing.constant.api.in.request.ConstantUpdateRequest;
import ee.qrent.billing.constant.api.in.response.constant.ConstantResponse;

import java.math.BigDecimal;

public interface GetConstantQuery extends BaseGetQuery<ConstantUpdateRequest, ConstantResponse> {

    ConstantResponse getByName(final String name);

    BigDecimal getFeeWeeklyInterest();

}


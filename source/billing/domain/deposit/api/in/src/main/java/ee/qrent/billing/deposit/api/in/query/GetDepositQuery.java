package ee.qrent.billing.deposit.api.in.query;

import ee.qrent.common.in.query.BaseGetQuery;
import ee.qrent.billing.deposit.api.in.request.DepositUpdateRequest;
import ee.qrent.billing.deposit.api.in.response.DepositResponse;

import java.math.BigDecimal;

public interface GetDepositQuery extends BaseGetQuery<DepositUpdateRequest, DepositResponse> {

    BigDecimal getPaidAmountByDriverId(final Long driverId);
}

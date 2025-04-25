package ee.qrent.billing.insurance.api.in.query;

import ee.qrent.common.in.query.BaseGetQuery;
import ee.qrent.billing.insurance.api.in.request.InsuranceCaseUpdateRequest;
import ee.qrent.billing.insurance.api.in.response.InsuranceCaseBalanceResponse;
import ee.qrent.billing.insurance.api.in.response.InsuranceCaseResponse;

import java.math.BigDecimal;
import java.util.List;

public interface GetInsuranceCaseQuery
    extends BaseGetQuery<InsuranceCaseUpdateRequest, InsuranceCaseResponse> {

  List<InsuranceCaseResponse> getActive();

  List<InsuranceCaseResponse> getActiveByDriverId(final Long driverId);

  List<InsuranceCaseResponse> getClosed();

  Long getCountActive();

  Long getCountClosed();

  List<InsuranceCaseBalanceResponse> getInsuranceCaseBalancesByInsuranceCase(
      final Long insuranceCaseId);

  BigDecimal getPaidAmountByInsuranceCaseId(final Long insuranceCaseId);
}

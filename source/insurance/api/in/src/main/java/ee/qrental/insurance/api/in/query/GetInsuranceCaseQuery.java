package ee.qrental.insurance.api.in.query;

import ee.qrent.common.in.query.BaseGetQuery;
import ee.qrental.insurance.api.in.request.InsuranceCaseUpdateRequest;
import ee.qrental.insurance.api.in.response.InsuranceCaseBalanceResponse;
import ee.qrental.insurance.api.in.response.InsuranceCaseResponse;

import java.util.List;

public interface GetInsuranceCaseQuery
    extends BaseGetQuery<InsuranceCaseUpdateRequest, InsuranceCaseResponse> {

  List<InsuranceCaseBalanceResponse> getInsuranceCaseBalancesByInsuranceCase(
      final Long insuranceCaseId);
}

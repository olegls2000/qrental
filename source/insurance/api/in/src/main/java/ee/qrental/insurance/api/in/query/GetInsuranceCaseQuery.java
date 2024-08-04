package ee.qrental.insurance.api.in.query;

import ee.qrent.common.in.query.BaseGetQuery;
import ee.qrental.insurance.api.in.request.InsuranceCaseUpdateRequest;
import ee.qrental.insurance.api.in.response.InsuranceCaseResponse;

public interface GetInsuranceCaseQuery
    extends BaseGetQuery<InsuranceCaseUpdateRequest, InsuranceCaseResponse> {}

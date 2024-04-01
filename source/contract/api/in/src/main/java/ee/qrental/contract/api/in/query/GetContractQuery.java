package ee.qrental.contract.api.in.query;

import ee.qrental.common.core.in.query.BaseGetQuery;
import ee.qrental.contract.api.in.request.ContractUpdateRequest;
import ee.qrental.contract.api.in.response.ContractResponse;

import java.util.List;

public interface GetContractQuery extends BaseGetQuery<ContractUpdateRequest, ContractResponse> {
  ContractResponse getActiveContractByDriverId(final Long driverId);

  List<String> getAllDurations();
}

package ee.qrent.billing.contract.api.in.query;

import ee.qrent.common.in.query.BaseGetQuery;
import ee.qrent.billing.contract.api.in.request.ContractUpdateRequest;
import ee.qrent.billing.contract.api.in.response.ContractResponse;

import java.util.List;

public interface GetContractQuery extends BaseGetQuery<ContractUpdateRequest, ContractResponse> {
  ContractResponse getLatestContractByDriverId(final Long driverId);

  ContractResponse getActiveContractByDriverIdAndQWeekId(final Long driverId, final Long qWekId);

  List<String> getAllDurations();

  List<ContractResponse> getAllActiveForCurrentDate();

  ContractResponse getCurrentActiveByDriverId(Long driverId);

  List<ContractResponse> getClosed();

  Long getCountActive();

  Long getCountClosed();
}

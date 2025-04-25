package ee.qrent.billing.contract.api.in.usecase;

import ee.qrent.billing.contract.api.in.request.ContractCloseRequest;
import ee.qrent.billing.contract.api.in.response.ContractPreCloseResponse;

public interface ContractCloseUseCase {
  ContractPreCloseResponse getPreCloseResponse(final Long contractId);

  void close(final ContractCloseRequest request);
}

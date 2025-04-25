package ee.qrent.billing.contract.api.in.usecase;

import ee.qrent.billing.contract.api.in.request.ContractSendByEmailRequest;

public interface ContractSendByEmailUseCase {

  void sendByEmail(final ContractSendByEmailRequest request);
}

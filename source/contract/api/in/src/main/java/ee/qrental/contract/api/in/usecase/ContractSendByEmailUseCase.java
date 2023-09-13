package ee.qrental.contract.api.in.usecase;

import ee.qrental.contract.api.in.request.ContractSendByEmailRequest;

public interface ContractSendByEmailUseCase {

  void sendByEmail(final ContractSendByEmailRequest request);
}

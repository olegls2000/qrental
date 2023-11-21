package ee.qrental.contract.api.in.usecase;

import ee.qrental.contract.api.in.request.AuthorizationBoltSendByEmailRequest;

public interface AuthorizationBoltSendByEmailUseCase {

  void sendByEmail(final AuthorizationBoltSendByEmailRequest request);
}

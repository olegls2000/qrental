package ee.qrental.contract.api.in.usecase;

import ee.qrental.contract.api.in.request.AuthorizationSendByEmailRequest;

public interface AuthorizationSendByEmailUseCase {

  void sendByEmail(final AuthorizationSendByEmailRequest request);
}
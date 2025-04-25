package ee.qrent.billing.contract.api.in.usecase;

import ee.qrent.billing.contract.api.in.request.AuthorizationSendByEmailRequest;

public interface AuthorizationSendByEmailUseCase {

  void sendByEmail(final AuthorizationSendByEmailRequest request);
}
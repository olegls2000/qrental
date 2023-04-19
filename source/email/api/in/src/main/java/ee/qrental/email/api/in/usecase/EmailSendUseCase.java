package ee.qrental.email.api.in.usecase;

import ee.qrental.email.api.in.request.EmailSendRequest;

public interface EmailSendUseCase {

  void sendEmail(final EmailSendRequest request);
}

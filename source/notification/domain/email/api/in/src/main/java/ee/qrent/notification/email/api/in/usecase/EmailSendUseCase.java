package ee.qrent.notification.email.api.in.usecase;

import ee.qrent.notification.email.api.in.request.EmailSendRequest;


public interface EmailSendUseCase {

  void sendEmail(final EmailSendRequest request);
}

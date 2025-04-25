package ee.qrent.notification.email.core.service;

import ee.qrent.notification.email.api.in.request.EmailSendRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.IOException;

public interface LetterBuildStrategy {
  boolean canApply(final EmailSendRequest emailSendRequest);

  MimeMessage process(final EmailSendRequest emailSendRequest, final MimeMessage message)
      throws MessagingException, IOException;
}

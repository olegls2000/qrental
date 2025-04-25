package ee.qrent.notification.email.core.service;

import ee.qrent.common.in.mapper.AddRequestMapper;
import ee.qrent.common.in.time.QDateTime;
import ee.qrent.notification.email.api.in.request.EmailSendRequest;
import ee.qrent.notification.email.domain.EmailNotification;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EmailSendRequestMapper
    implements AddRequestMapper<EmailSendRequest, EmailNotification> {

  private final QDateTime dateTime;

  @Override
  public EmailNotification toDomain(final EmailSendRequest request) {

    return EmailNotification.builder()
        .id(null)
        .type(request.getType().name())
        .recipients(request.getRecipients())
        .properties(request.getProperties())
        .sentAt(dateTime.getNow())
        .build();
  }
}

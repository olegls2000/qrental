package ee.qrent.notification.email.persistence.mapper;

import ee.qrent.notification.email.domain.EmailNotification;
import ee.qrent.notification.email.persistence.entity.jakarta.EmailNotificationJakartaEntity;
import ee.qrent.notification.email.persistence.entity.jakarta.EmailNotificationPayloadJson;

public class EmailNotificationAdapterMapper {

  public EmailNotification mapToDomain(final EmailNotificationJakartaEntity entity) {

    return EmailNotification.builder()
        .id(entity.getId())
        .type(entity.getType())
        .recipients(entity.getPayload().getRecipients())
        .properties(entity.getPayload().getProperties())
        .sentAt(entity.getSentAt())
        .build();
  }

  public EmailNotificationJakartaEntity mapToEntity(final EmailNotification domain) {
    return EmailNotificationJakartaEntity.builder()
        .id(domain.getId())
        .type(domain.getType())
        .sentAt(domain.getSentAt())
        .payload(
            EmailNotificationPayloadJson.builder()
                .recipients(domain.getRecipients())
                .properties(domain.getProperties())
                .build())
        .build();
  }
}

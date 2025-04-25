package ee.qrent.queue.persistence.mapper;

import ee.qrent.queue.domain.NotificationPayload;
import ee.qrent.queue.domain.QueueEntry;
import ee.qrent.queue.persistence.entity.jakarta.NotificationPayloadJson;
import ee.qrent.queue.persistence.entity.jakarta.QueueEntryJakartaEntity;

public class QueueEntryAdapterMapper {

  public QueueEntry mapToDomain(final QueueEntryJakartaEntity entity) {

    return QueueEntry.builder()
        .id(entity.getId())
        .occurredAt(entity.getOccurredAt())
        .publishedAt(entity.getPublishedAt())
        .processed(entity.getProcessed())
        .processedAt(entity.getProcessedAt())
        .payload(mapToDomain(entity.getPayload()))
        .build();
  }

  public QueueEntryJakartaEntity mapToEntity(final QueueEntry domain) {

    return QueueEntryJakartaEntity.builder()
        .id(domain.getId())
        .occurredAt(domain.getOccurredAt())
        .publishedAt(domain.getPublishedAt())
        .processed(domain.getProcessed())
        .processedAt(domain.getProcessedAt())
        .payload(mapToEntity(domain.getPayload()))
        .build();
  }

  private NotificationPayload mapToDomain(final NotificationPayloadJson payloadJson) {

    return NotificationPayload.builder()
        .recipients(payloadJson.getRecipients())
        .type(payloadJson.getType())
        .attachmentBytes(payloadJson.getAttachment())
        .properties(payloadJson.getProperties())
        .build();
  }

  private NotificationPayloadJson mapToEntity(final NotificationPayload domain) {

    return NotificationPayloadJson.builder()
        .recipients(domain.getRecipients())
        .type(domain.getType())
        .attachment(domain.getAttachmentBytes())
        .properties(domain.getProperties())
        .build();
  }
}

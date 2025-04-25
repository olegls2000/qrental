package ee.qrent.queue.core;

import ee.qrent.common.in.time.QDateTime;
import ee.qrent.queue.api.in.QueueEntryPushRequest;
import ee.qrent.queue.domain.NotificationPayload;
import ee.qrent.queue.domain.QueueEntry;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@AllArgsConstructor
public class QueueEntryPushRequestMapper {

  private static final boolean DEFAULT_PROCESSED = false;

  private final QDateTime qDateTime;

  @SneakyThrows
  public QueueEntry toDomain(final QueueEntryPushRequest pushRequest) {

    return QueueEntry.builder()
        .id(null)
        .occurredAt(pushRequest.getOccurredAt())
        .processed(DEFAULT_PROCESSED)
        .processedAt(null)
        .publishedAt(qDateTime.getNow())
        .payload(mapPayload(pushRequest))
        .build();
  }

  @SneakyThrows
  private NotificationPayload mapPayload(final QueueEntryPushRequest pushRequest) {

    return NotificationPayload.builder()
        .recipients(pushRequest.getPayloadRecipients())
        .type(pushRequest.getType().name())
        .attachmentBytes(
            pushRequest.getPayloadAttachment() == null
                ? null
                : pushRequest.getPayloadAttachment().readAllBytes())
        .properties(pushRequest.getPayloadProperties())
        .build();
  }
}

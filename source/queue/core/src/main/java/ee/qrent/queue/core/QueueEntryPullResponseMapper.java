package ee.qrent.queue.core;

import ee.qrent.queue.api.in.EntryType;
import ee.qrent.queue.api.in.QueuePullResponse;
import ee.qrent.queue.domain.QueueEntry;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class QueueEntryPullResponseMapper {
  QueuePullResponse toResponse(final QueueEntry domain) {

    return QueuePullResponse.builder()
        .id(domain.getId())
        .occurredAt(domain.getOccurredAt())
        .publishedAt(domain.getPublishedAt())
        .processed(domain.getProcessed())
        .processedAt(domain.getProcessedAt())
        .payloadRecipients(domain.getPayload().getRecipients())
        .payloadType(EntryType.valueOf(domain.getPayload().getType()))
        .payloadAttachment(getAttachmentInputStream(domain))
        .payloadProperties(domain.getPayload().getProperties())
        .build();
  }

  private InputStream getAttachmentInputStream(final QueueEntry domain) {
    final var bytesArray = domain.getPayload().getAttachmentBytes();

    if (bytesArray == null) {
      return null;
    }
    return new ByteArrayInputStream(bytesArray);
  }
}

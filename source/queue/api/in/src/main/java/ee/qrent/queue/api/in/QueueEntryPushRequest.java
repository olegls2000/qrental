package ee.qrent.queue.api.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@SuperBuilder
@Getter
public class QueueEntryPushRequest {
  private final LocalDateTime occurredAt;
  private final List<String> payloadRecipients;
  private final EntryType type;
  private final InputStream payloadAttachment;
  private final Map<String, Object> payloadProperties;
}

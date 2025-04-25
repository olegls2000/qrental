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
public class QueuePullResponse {
  private Long id;
  private LocalDateTime occurredAt;
  private LocalDateTime publishedAt;
  private Boolean processed;
  private LocalDateTime processedAt;
  private final List<String> payloadRecipients;
  private final EntryType payloadType;
  private final InputStream payloadAttachment;
  private final Map<String, Object> payloadProperties;
}

package ee.qrent.queue.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import java.time.LocalDateTime;

@SuperBuilder
@Getter
@Setter
public class QueueEntry {
  private Long id;
  private LocalDateTime occurredAt;
  private LocalDateTime publishedAt;
  private Boolean processed;
  private LocalDateTime processedAt;
  private final NotificationPayload payload;
}

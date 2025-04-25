package ee.qrent.notification.email.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import lombok.Getter;

import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class EmailNotification {
  private final Long id;
  private final String type;
  private final List<String> recipients;
  private final Map<String, Object> properties;
  private final LocalDateTime sentAt;
}

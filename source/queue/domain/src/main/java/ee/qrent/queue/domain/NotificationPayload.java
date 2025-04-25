package ee.qrent.queue.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

@SuperBuilder
@Getter
@Setter
public class NotificationPayload {
  private final List<String> recipients;
  private final String type;
  private final byte[] attachmentBytes;
  private final Map<String, Object> properties;
}

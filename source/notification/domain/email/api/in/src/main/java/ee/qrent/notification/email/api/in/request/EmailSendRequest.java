package ee.qrent.notification.email.api.in.request;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@ToString
public class EmailSendRequest {
  private final EmailType type;
  private final List<String> recipients;
  private final InputStream attachment;
  private final Map<String, Object> properties;
}

package ee.qrental.email.api.in.request;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class EmailSendRequest {
  private final List<String> recipients;
  private final EmailType type;
  private final InputStream attachment;
  private final Map<String, Object> properties;
}

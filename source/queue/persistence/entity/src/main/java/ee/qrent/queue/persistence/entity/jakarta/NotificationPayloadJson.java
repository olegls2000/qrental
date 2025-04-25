package ee.qrent.queue.persistence.entity.jakarta;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Getter
@SuperBuilder
@ToString
@NoArgsConstructor
public class NotificationPayloadJson {
  private List<String> recipients;
  private String type;
  private byte[] attachment;
  private Map<String, Object> properties;
}

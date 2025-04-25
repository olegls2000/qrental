package ee.qrent.notification.email.persistence.entity.jakarta;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class EmailNotificationPayloadJson {
  private List<String> recipients;
  private Map<String, Object> properties;
}

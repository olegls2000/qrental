package ee.qrent.billing.driver.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public class CallSign {
  private Long id;
  private Integer callSign;
  private String comment;
}

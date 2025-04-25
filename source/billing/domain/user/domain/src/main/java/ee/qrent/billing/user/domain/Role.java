package ee.qrent.billing.user.domain;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class Role {
  private Long id;
  private String name;
  private String comment;
}

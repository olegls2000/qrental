package ee.qrental.driver.domain;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class Role {
  private Long id;
  private String name;
  private String comment;
}

package ee.qrental.user.api.in.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class RoleResponse {
  private Long id;
  private String name;
  private String comment;
}

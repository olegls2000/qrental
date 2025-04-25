package ee.qrent.billing.user.api.in.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class UserAccountResponse {
  private Long id;
  private String username;
  private String password;
  private String email;
  private String firstName;
  private String lastName;
  private int rolesCount;
}

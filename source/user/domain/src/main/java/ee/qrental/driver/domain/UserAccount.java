package ee.qrental.driver.domain;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public class UserAccount {
  private Long id;
  private String username;
  private String email;
  private String password;
  private String firstName;
  private String lastName;
  private List<Role> roles;
}

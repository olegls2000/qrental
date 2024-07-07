package ee.qrental.user.api.in.request;

import java.util.Set;

import ee.qrent.common.in.request.AbstractUpdateRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class UserAccountUpdateRequest extends AbstractUpdateRequest {

  private String username;
  private String email;
  private String firstName;
  private String lastName;
  private Set<Long> roles;
}

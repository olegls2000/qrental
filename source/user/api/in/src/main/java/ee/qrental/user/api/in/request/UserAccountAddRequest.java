package ee.qrental.user.api.in.request;

import ee.qrent.common.in.request.AbstractAddRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserAccountAddRequest extends AbstractAddRequest {
  private String username;
  private String email;
  private String firstName;
  private String lastName;
  private List<Long> roles;
}
